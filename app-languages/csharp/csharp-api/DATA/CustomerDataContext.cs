using Microsoft.EntityFrameworkCore;

namespace csharp_api.DATA
{
    public class CustomerDataContext: DbContext
    {
        public CustomerDataContext(DbContextOptions<CustomerDataContext> options): base(options) { }
        public DbSet<Order> orders { get; set; }
    }
}
