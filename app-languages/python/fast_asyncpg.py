from fastapi import FastAPI
import asyncpg
import orjson
from fastapi.responses import ORJSONResponse

def stressGarbageCollectorSort(arr):
    if len(arr) <= 1:
        return arr

    pivot = arr[len(arr) // 2]
    less = []
    equal = []
    greater = []

    for element in arr:
        if element < pivot:
            less.append(element)
        elif element > pivot:
            greater.append(element)
        else:
            equal.append(element)

    return stressGarbageCollectorSort(less) + equal + stressGarbageCollectorSort(greater)

app = FastAPI()

@app.on_event("startup")
async def startup():
    global pool
    pool = await asyncpg.create_pool(
                                user='admin',
                                password='admin',
                                database='dev-db',
                                host='postgres',
                                port=5432
    )

@app.get("/")
async def orjson():
    global pool
    async with pool.acquire() as conn:
        values = await conn.fetch(
            'SELECT * FROM "orders" LIMIT 10'
        )
        
        r = [dict(v) for v in values]

        input = [
                    9, 13, 7, 25, 18, 3, 11, 30, 14, 6,
                    22, 17, 4, 28, 12, 19, 8, 15, 1, 10,
                    27, 5, 21, 16, 2, 24, 20, 23, 29, 26,
                    35, 39, 33, 51, 44, 31, 47, 36, 43, 32,
                    48, 38, 45, 34, 50, 37, 42, 40, 49, 41
                ]
        sorted = stressGarbageCollectorSort(input)

        response_data = {
            "query": r,
            "sorted": sorted
        }
        return ORJSONResponse(content=response_data)