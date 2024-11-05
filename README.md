# Middleware Engineering "REST and Data Formats"

## Aufgabenstellung

Gegeben sind Daten von einem Wahllokal. Jeder Bezirk will diese über eine REST Schnittstelle veröffentlichen. Formate sind JSON und XML:

Beispiel aus dem Kurs:

```xml
<electionData>
  <regionID>33123</regionID>
  <regionName>Linz Bahnhof</regionName>
  <regionAddress>Bahnhofsstrasse 27/9</regionAddress>
  <regionPostalCode>Linz</regionPostalCode>
  <federalState>Austria</federalState>
  <timestamp>2024-09-12 11:48:21</timestamp>
  <countingData>
    <party>
      <partyID>OEVP</partyID>
      <amountVotes>322</amountVotes>
    </party>
    <party>
      <partyID>SPOE</partyID>
      <amountVotes>301</amountVotes>
    </party>
    <party>
      <partyID>FPOE</partyID>
      <amountVotes>231</amountVotes>
    </party>
    <party>
      <partyID>GRUENE</partyID>
      <amountVotes>211</amountVotes>
    </party>
    <party>
      <partyID>NEOS</partyID>
      <amountVotes>182</amountVotes>
    </party>
  </countingData>
</electionData>
```

## Implementierung

### WarehouseApplication.java

Dies ist die Startklasse der Spring-Boot Anwendung. Der wichtigste Teil der Klasse ist die Main-Methode: 


```
	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}
```

Der erste Parameter besagt die Hauptkonfigurationsklase. Von dort kann dann alles gestartet werden. Da die Klasse auch mit einem `@SpringBootApplication` versehen ist weiß das Framwork automatisch, dass es von hier starten soll.

### Webconfig.java

Diese Klasse wurde hinzugefügt damit die XML-Notation funktioniert. Wichtig ist der `@Configuration`-Tag damit erkannt wird vom Framwork, dass es eine Konfigurationdatei ist. Diese Klasse wird also als Teil von Spring-Kontext geladen.

Durch die implementierung von `WebMvcConfigurer` können spezifische Einstellung für HTTP- Anfragen und Antworten vorgenommen werden. 

```java
@Bean
public MappingJackson2XmlHttpMessageConverter xmlHttpMessageConverter() {
 return new MappingJackson2XmlHttpMessageConverter();
}
```

Diese Methode macht die Anwendung fähig XML-Daten mit HTTP-Anfragen und Antworten zu managen. 

```
@Override
public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(xmlHttpMessageConverter());
}

```

Diese Methode fügt den XML-Konverter zur Liste der HTTP-Nachrichtenkonverter hinzu. Damit stellt sie sicher, dass XML-basierte Nachrichten korrekt verarbeitet werden können.

### model-Ordner

#### WarehouseData.java

Das ist die Grund-Datei welche die Attribut bzw. die XML-Elemente der Wahlergebnise angibt. Dazu wurden für die verschiedenen Angaben Strings in Java verwendet ausgenommen von der "Counding Data" diese ist in einer Liste angegeben. 

Wichtig ist der Tag `@XmlElement` dieser macht den Framework klar was ein neues XML-Element ist

#### PartyVotes.java

Diese Klasse besteht aus 3 Atribut. Zuerst der Name der Partei als String, danach die Anzahl an Stimmen als Integer und eine Liste mit den "Preferred Votes". 

#### PreferredVote.java

Politsch gesehen hat jede Partei innerhalb ein "ranking" welches entscheided wer in den Nationalrat darf. Jedoch kann man als Wähler angeben wen man eine zusätzliche Stimme gibt wodurch diese Person eher einen Platz bekommet. 

Diese Klasse setzt dies um.

Dazu gibt es 3 Attribut nämlich 2 Integer welcher einer davon die Number in der Liste ist und der 2. ist die Anzahl an Stimmen. Das dritte Attribut ist der Name als String.

### warehouse-Ordner

#### WarehouseService.java

Diese Datei ist ein Service welcher durch den Tag `@Service` für Sprinboot sichtbar gemacht wird. Diese wird in das Framework mit aufgenommen und kann somit einfach aufgerufen werden. Eine Service-Datei beinhaltete die Logik des Programmes. In diesem Fall gibt es die Daten an den Controler zurück. Dazu wird folgende Methode verwendet.

```java
    public WarehouseData getWarehouseData(String inID) {
        WarehouseSimulation simulation = new WarehouseSimulation();
        return simulation.getData(inID);
    }
```

#### WarehouseSimulation.java

Diese Klasse erstellt simulierte Testdaten zur Verfügung. Der unterschied zu einer richtigen Anwendung ist, dass hier die Wahlrgebniss mit einer Zuffalsmethode immer leicht unterschiedlich sind jedoch eine ungefähre Ähnlichkeit zur realität hat.

Diese werden einfach durch Methoden erstellt und zurückgegeben. Die `getData();` Methode erstellt die Wahlergebnisse und eine sperate Methode erstellt die Vorzugsstimmen welche `generatePreferredVotes()` heißt.

#### WarehouseController.java

Damit die Klasse als Controler angesehen wird und auch das Mapping für das Warehouse funktioniert braucht die Klasse folgende 2 Tags:


```
@RestController
@RequestMapping("/warehouse")
```

Die erste Annotation besagt dass dies eine Rest-Schnittstelle ist und die zweite den Grundpfad welcher `/warehouse/` ist.

Der Body hat folgende 3 wichtige Methoden:

```java
 @Autowired
 private WarehouseService service;

 @GetMapping(value = "/{inID}", produces = MediaType.APPLICATION_JSON_VALUE)
 @ResponseBody
 public WarehouseData getWarehouseDataJson(@PathVariable String inID) {
     return service.getWarehouseData(inID);
 }

 @GetMapping(value = "/{inID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
 @ResponseBody
 public WarehouseData getWarehouseDataXml(@PathVariable String inID) {
     return service.getWarehouseData(inID);
 }


```

Die Annotation `@Autowired` bindet den Service ein.

Damit das Mapping funktioniert muss folgende Annotation genommen werden:

```
 @GetMapping(value = "Verzeichnis", produces = "Als was es zurückgegeben wird")
```

Bedeutet wenn im Browser folgender Link aufgerufen wird: `"http://localhost:8080/warehouse/001"` Die Annotation lässt Springboot erkennen was verlangt wird. Dadurch kann dann die Methode aufgerufen werden.

## Quellen

https://spring.io/projects/spring-boot [Spring Boot, Accessed 2024-11-03]

https://spring.io/guides/gs/rest-service/ [Building a RESTful Web Service, Accessed 2024-11-03]

https://spring.io/guides/gs/consuming-rest/ [Consuming a RESTful Web Service, Accessed 2024-11-03]

https://www.redhat.com/de/topics/api/what-is-a-rest-api [What is a REST API?, Accessed 2024-11-03]

https://www.baeldung.com/spring-bean [Spring @Bean Annotation, Accessed 2024-11-03]

https://howtodoinjava.com/spring-mvc/spring-webmvcconfigurer/ [Spring WebMvcConfigurer Interface, Accessed 2024-11-03]

https://www.concretepage.com/spring-4/spring-4-rest-xml-response-example-with-jackson-2 [Spring 4 REST XML, Accessed 2024-11-03]