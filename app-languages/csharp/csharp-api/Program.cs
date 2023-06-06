using Microsoft.EntityFrameworkCore;
using Npgsql;
using csharp_api.DBContext;
using csharp_api.Interfaces;
using csharp_api.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<ApplicationDbContext>(options => options.UseNpgsql(builder.Configuration.GetConnectionString("devDB")));
builder.Services.AddScoped<IApplicationDbContext>(provider => provider.GetService<ApplicationDbContext>()!);
builder.Services.AddTransient<IOrderService, OrderService>();
builder.Services.AddControllers();

var app = builder.Build();
// Configure the HTTP request pipeline.

app.UseAuthorization();
app.MapControllers();
app.Run();