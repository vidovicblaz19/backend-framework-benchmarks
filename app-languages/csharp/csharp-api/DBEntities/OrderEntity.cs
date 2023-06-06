using System.ComponentModel.DataAnnotations;

namespace csharp_api.DBEntities
{
    public class OrderEntity
    {
        [Key]
        public int order_id { get; set; }
        public string? customer_id { get; set; }
        public int? employee_id { get; set; }
        public DateTime? order_date { get; set; }
        public DateTime? required_date { get; set; }
        public DateTime? shipped_date { get; set; }
        public int? ship_via { get; set; }
        public double? freight { get; set; }
        public string? ship_name { get; set; }
        public string? ship_address { get; set; }
        public string? ship_city { get; set; }
        public string? ship_region { get; set; }
        public string? ship_postal_code { get; set; }
        public string? ship_country { get; set; }
    }
}
