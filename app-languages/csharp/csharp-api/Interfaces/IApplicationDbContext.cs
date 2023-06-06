using Microsoft.EntityFrameworkCore;
using csharp_api.DBEntities;

namespace csharp_api.Interfaces
{
    public interface IApplicationDbContext
    {
        DbSet<OrderEntity>? orders { get; set; }
    }
}
