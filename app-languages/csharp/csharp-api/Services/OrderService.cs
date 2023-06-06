using Microsoft.EntityFrameworkCore;
using csharp_api.DBContext;
using csharp_api.DBEntities;
using csharp_api.Interfaces;

namespace csharp_api.Services
{
    public class OrderService : IOrderService
    {
        private readonly ApplicationDbContext _applicationDbContext;

        public OrderService(ApplicationDbContext applicationDbContext)
        {
            _applicationDbContext = applicationDbContext;
        }

        public async Task<List<OrderEntity>> GetOrdersAsync()
        {
            return await _applicationDbContext.orders!.AsNoTracking().Take(10).ToListAsync<OrderEntity>();
        }
    }
}