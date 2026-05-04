using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi;
using Swashbuckle.AspNetCore.SwaggerGen;
using Microsoft.AspNetCore.Cors.Infrastructure; 
using System.Text;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddEndpointsApiExplorer();

builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
}).AddJwtBearer(options =>
{
    options.Authority = "https://localhost:5001"; // IdentityServer URL
    options.Audience = "myApi"; // API resource name
    options.TokenValidationParameters.ValidateLifetime = true;
    options.TokenValidationParameters.ValidateIssuer = true;
    options.TokenValidationParameters.ClockSkew = TimeSpan.FromMinutes(5);
});
builder.Services.AddCors();
builder.Services.AddAuthorization(options=>
options.AddPolicy("SurveyCreator", 
policy => policy.RequireRole("SurveyCreator")));
builder.Services.AddSwaggerGen(static c =>
{
    c.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme()
    {
        Name = "Authorization",
        Type = SecuritySchemeType.Http,
        Scheme = "Bearer",
        BearerFormat = "JWT",
        In = ParameterLocation.Header,
        Description = "JWT Authorization header required"
    });
});

var app = builder.Build();

app.UseCors();
app.UseMiddleware<AuthenticationMiddleware>(); 
app.UseAuthorization();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.MapGet("amnaAuthintication", () =>
{
    return Results.Ok("Authinticated!");
    }).RequireAuthorization();


//app.MapPost("/index", (HttpContext context, LoginRequest request) =>
//{
//    if (request.Username == "amna" && request.Password == "2003")
//    {
//        context.Response.Cookies.Append("username", request.Username);
//        return Results.Ok(new
//        {
//            status = "success",
//            message = "Login successful!"
//        });
//    }
//    return Results.BadRequest(new
//    {
//        status = "Error",
//        message = "Invalid username or password"
//    });
//});

//app.MapGet("/profile", (HttpContext context) =>
//{
//    var username = context.Request.Cookies["username"];
//    if (string.IsNullOrEmpty(username))
//    {
//        return Results.BadRequest(new
//        {
//            status = "Error",
//            message = "User not logged in"
//        });
//    }
//    return Results.Ok(new
//    {
//        status = "success",
//        message = $"Welcome {username}"
//    });
//});

//app.MapGet("/logout", (HttpContext context) =>
//{
//    context.Response.Cookies.Delete("username");
//    return Results.Ok(new
//    {
//        status = "success",
//        message = "Logged out successfully"
//    });
//});

app.Run();

//public class LoginRequest
//{
//    public string Username { get; set; } = "";
//    public string Password { get; set; } = "";
//}

