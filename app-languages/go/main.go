package main

import (
	"context"
	"database/sql"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"sync"

	"github.com/jackc/pgx/v5/pgxpool"
)

func stressGarbageCollectorSort(arr []int) []int {
	if len(arr) <= 1 {
		return arr
	}

	pivot := arr[len(arr)/2]
	less := make([]int, 0)
	equal := make([]int, 0)
	greater := make([]int, 0)

	for _, element := range arr {
		if element < pivot {
			less = append(less, element)
		} else if element > pivot {
			greater = append(greater, element)
		} else {
			equal = append(equal, element)
		}
	}

	return append(append(stressGarbageCollectorSort(less), equal...), stressGarbageCollectorSort(greater)...)
}

var db *pgxpool.Pool

var responseWriterPool = sync.Pool{
	New: func() interface{} {
		return &responseWriter{}
	},
}

type responseWriter struct {
	http.ResponseWriter
}

type Order struct {
	Order_id         int            `json:"order_id"`
	Customer_id      sql.NullString `json:"customer_id"`
	Employee_id      sql.NullInt32  `json:"employee_id"`
	Order_date       sql.NullTime   `json:"order_date"`
	Required_date    sql.NullTime   `json:"required_date"`
	Shipped_date     sql.NullTime   `json:"shipped_date"`
	Ship_via         sql.NullInt32  `json:"ship_via"`
	Freight          float64        `json:"freight"`
	Ship_name        sql.NullString `json:"ship_name"`
	Ship_address     sql.NullString `json:"ship_address"`
	Ship_city        sql.NullString `json:"ship_city"`
	Ship_region      sql.NullString `json:"ship_region"`
	Ship_postal_code sql.NullString `json:"ship_postal_code"`
	Ship_country     sql.NullString `json:"ship_country"`
}

type Out struct {
	Query  []Order `json:"query"`
	Sorted []int   `json:"sorted"`
}

func requestHandlerFunction(w http.ResponseWriter, req *http.Request) {
	rw := responseWriterPool.Get().(*responseWriter)
	defer responseWriterPool.Put(rw)
	rw.ResponseWriter = w

	rows, err := db.Query(context.Background(), "SELECT * FROM orders LIMIT 10")
	if err != nil {
		http.Error(rw, err.Error(), http.StatusInternalServerError)
		return
	}
	defer rows.Close()

	orders := []Order{}

	for rows.Next() {
		var order Order
		err := rows.Scan(&order.Order_id,
			&order.Customer_id,
			&order.Employee_id,
			&order.Order_date,
			&order.Required_date,
			&order.Shipped_date,
			&order.Ship_via,
			&order.Freight,
			&order.Ship_name,
			&order.Ship_address,
			&order.Ship_city,
			&order.Ship_region,
			&order.Ship_postal_code,
			&order.Ship_country,
		)
		if err != nil {
			http.Error(rw, err.Error(), http.StatusInternalServerError)
			return
		}
		orders = append(orders, order)
	}

	if err := rows.Err(); err != nil {
		http.Error(rw, "Internal server error row", http.StatusInternalServerError)
		return
	}

	input := []int{
		9, 13, 7, 25, 18, 3, 11, 30, 14, 6,
		22, 17, 4, 28, 12, 19, 8, 15, 1, 10,
		27, 5, 21, 16, 2, 24, 20, 23, 29, 26,
		35, 39, 33, 51, 44, 31, 47, 36, 43, 32,
		48, 38, 45, 34, 50, 37, 42, 40, 49, 41,
	}

	sorted := stressGarbageCollectorSort(input)

	out := Out{
		Query:  orders,
		Sorted: sorted,
	}

	jsonData, err := json.Marshal(out)
	if err != nil {
		http.Error(rw, "Internal server error json", http.StatusInternalServerError)
		return
	}

	rw.Header().Set("Content-Type", "application/json")
	rw.Write(jsonData)
}

func main() {
	//runtime.GOMAXPROCS(2) // Set the maximum number of CPU cores

	var err error
	db, err = pgxpool.New(context.Background(), "postgres://admin:admin@postgres:5432/dev-db")
	if err != nil {
		log.Fatalln("Unable to create connection pool:", err)
	}

	http.HandleFunc("/", requestHandlerFunction)

	port := ":7222" // Change this if you want to use a different port
	fmt.Printf("Server running at http://localhost%s/\n", port)
	log.Fatal(http.ListenAndServe(port, nil))
}
