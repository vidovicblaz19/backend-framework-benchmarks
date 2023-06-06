using csharp_api.DBEntities;

namespace csharp_api.Interfaces
{
    public interface IOrderService
    {
        Task<List<OrderEntity>> GetOrdersAsync();
    }
}
