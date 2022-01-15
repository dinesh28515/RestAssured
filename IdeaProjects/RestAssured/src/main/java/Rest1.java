import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Rest1 {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com/";
        String response=given().log().all().queryParam("key","qaclick123").header("Content-type","application/json")
                .body(payload.AddPlace())
                .when().post("/maps/api/place/add/json")


                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.18 (Ubuntu)")
                .extract().response().asString();
        System.out.println(response);
        JsonPath js=new JsonPath(response);//for parsing json
        String placeId=js.getString("place_id");
        System.out.println(placeId);

        //update place
    String newAddress= "winter walk, africa";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n" +
                        " ")
                .when().put("maps/api/place/update/json").
                then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
    //get request
                given().log().all()
                .queryParam("place_id",placeId).
                queryParam("key","qaclick123").
                when().get("maps/api/place/get/json").
                then().assertThat().log().all().statusCode(200).body("address",equalTo("winter walk, africa"));
                /*System.out.println(getPlaceResponse);
                JsonPath js1=new JsonPath("getPlaceResponse");

                String actualAddress=js1.getString("latitude");
                System.out.println(actualAddress);*/

    }
}
