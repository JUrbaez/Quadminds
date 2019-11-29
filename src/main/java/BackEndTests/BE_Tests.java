package BackEndTests;

import static io.restassured.RestAssured.when;
import org.jsoup.Jsoup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class BE_Tests {
	
	static String baseURL_1;
	static String baseURL_2;
	static String endpoint;
	
	@BeforeSuite
	public static void setUpTests() 
	{
		baseURL_1 = "https://api.mercadolibre.com/sites/MLA/";
		baseURL_2 = "https://api.mercadolibre.com/items/";
		endpoint = "search?q=motorola";
	}
	
	@Test(description = "Verifica total de productos")
    public static void verifyProductsTotal() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        int numberOfProducts = responseAboutUs.path("paging.total");
        
        Assert.assertTrue(numberOfProducts>0);
    }
	
	@Test(description = "Verifica productos por pagina")
    public static void verifyProductsPaging() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        int numberOfProducts = responseAboutUs.path("paging.primary_results");
        int paging = responseAboutUs.path("paging.limit");

        System.out.print(numberOfProducts);
        System.out.print(paging);
        
        Assert.assertTrue(numberOfProducts<paging);
    }
	
	@Test(description = "Verifica el titulo de un producto")
    public static void verifyProductTitle() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString2 = Jsoup.parse(responseAboutUs.path("results[0].id")).text();
        String title1 = Jsoup.parse(responseAboutUs.path("results[0].title")).text();

        RestAssured.baseURI = baseURL_2;
        Response responseAboutUs2 = when().get(jsonAsString2).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString3 = Jsoup.parse(responseAboutUs2.path("title")).text();
        
        Assert.assertEquals(title1,jsonAsString3);
    }
	
	@Test(description = "Verifica el precio de un producto")
    public static void verifyProductPrice() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString2 = Jsoup.parse(responseAboutUs.path("results[0].id")).text();
        String title1 = Jsoup.parse(responseAboutUs.path("results[0].price").toString()).text();

        RestAssured.baseURI = baseURL_2;
        Response responseAboutUs2 = when().get(jsonAsString2).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString3 = Jsoup.parse(responseAboutUs2.path("price").toString()).text();
        
        Assert.assertTrue(title1.equalsIgnoreCase(jsonAsString3));
    }
	
	@Test(description = "Verifica si el producto acepta mercado pago")
    public static void verifyMercadoPago() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString2 = Jsoup.parse(responseAboutUs.path("results[0].id")).text();
        String title1 = Jsoup.parse(responseAboutUs.path("results[0].accepts_mercadopago").toString()).text();

        RestAssured.baseURI = baseURL_2;
        Response responseAboutUs2 = when().get(jsonAsString2).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString3 = Jsoup.parse(responseAboutUs2.path("accepts_mercadopago").toString()).text();
        
        Assert.assertTrue(title1.equalsIgnoreCase(jsonAsString3));
    }
	
	@Test(description = "Verifica tipo de moneda para el pago")
    public static void verifyCurrency() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString2 = Jsoup.parse(responseAboutUs.path("results[0].id")).text();
        String title1 = Jsoup.parse(responseAboutUs.path("results[0].currency_id").toString()).text();

        RestAssured.baseURI = baseURL_2;
        Response responseAboutUs2 = when().get(jsonAsString2).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString3 = Jsoup.parse(responseAboutUs2.path("currency_id").toString()).text();
        
        Assert.assertTrue(title1.equalsIgnoreCase(jsonAsString3));
    }
	
	@Test(description = "Verifica envio grais")
    public static void verifyFreeShipping() 
    {
		RestAssured.baseURI = baseURL_1;
        Response responseAboutUs = when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString2 = Jsoup.parse(responseAboutUs.path("results[0].id")).text();
        String title1 = Jsoup.parse(responseAboutUs.path("results[0].shipping.free_shipping").toString()).text();

        RestAssured.baseURI = baseURL_2;
        Response responseAboutUs2 = when().get(jsonAsString2).then().contentType(ContentType.JSON).extract().response();
        String jsonAsString3 = Jsoup.parse(responseAboutUs2.path("shipping.free_shipping").toString()).text();
        
        Assert.assertTrue(title1.equalsIgnoreCase(jsonAsString3));
    }
}
