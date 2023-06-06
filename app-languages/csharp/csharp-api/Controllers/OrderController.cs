using csharp_api.DBEntities;
using csharp_api.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace csharp_api.Controllers
{
    [Route("/")]
    [ApiController]
    public class OrderController : ControllerBase
    {
        private List<int> StressGarbageCollectorSort(List<int> arr)
        {
            if (arr.Count <= 1)
            {
                return arr;
            }

            int pivot = arr[arr.Count / 2];
            List<int> less = new List<int>();
            List<int> equal = new List<int>();
            List<int> greater = new List<int>();

            foreach (int element in arr)
            {
                if (element < pivot)
                {
                    less.Add(element);
                }
                else if (element > pivot)
                {
                    greater.Add(element);
                }
                else
                {
                    equal.Add(element);
                }
            }

            return StressGarbageCollectorSort(less)
                .Concat(equal)
                .Concat(StressGarbageCollectorSort(greater))
                .ToList();
        }


        readonly IOrderService? _orderService;

        public OrderController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        // POST <OrderController>
        [HttpGet]
        public async Task<IActionResult?> GetOrdersAsync()
        {
            var input = new List<int> {
                    9, 13, 7, 25, 18, 3, 11, 30, 14, 6,
                    22, 17, 4, 28, 12, 19, 8, 15, 1, 10,
                    27, 5, 21, 16, 2, 24, 20, 23, 29, 26,
                    35, 39, 33, 51, 44, 31, 47, 36, 43, 32,
                    48, 38, 45, 34, 50, 37, 42, 40, 49, 41
                };
            var sorted = StressGarbageCollectorSort(input);

            try
            {
                List<OrderEntity> orders = await _orderService.GetOrdersAsync();
                return Ok(new { query = orders, sorted = sorted });
            }
            catch(Exception ex)
            {
                //Log the exception
                Console.WriteLine(ex);
                return null;
            }
        }
    }
}
