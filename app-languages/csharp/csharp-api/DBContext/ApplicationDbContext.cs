using Microsoft.EntityFrameworkCore;
using csharp_api.DBEntities;
using csharp_api.Interfaces;

namespace csharp_api.DBContext
{
    public class ApplicationDbContext : DbContext, IApplicationDbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        public DbSet<OrderEntity>? orders { get; set; }
    }
}
