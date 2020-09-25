package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymanski.paker.models.*;
import pl.szymanski.paker.models.enums.ECarType;
import pl.szymanski.paker.models.enums.EProvince;
import pl.szymanski.paker.models.enums.ERole;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.RouteResponse;
import pl.szymanski.paker.repository.*;
import pl.szymanski.paker.services.CalculateRouteService;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/db/")
public class DBLoaderController {

    @Autowired
    private RoleRepo role_R;
    @Autowired
    private ProvinceRepo prov_R;
    @Autowired
    private MaintenanceRepo main_R;
    @Autowired
    private CarRepo car_R;
    @Autowired
    private CarTypeRepo carType_R;
    @Autowired
    private UserRepo user_R;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CityRepo city_R;
    @Autowired
    private CalculateRouteService routeService;

    @GetMapping("load_admin")
    public ResponseEntity<MessageResponse> initiateAdmin() {

        if (user_R.existsByUsername("admin")) {
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Admin is already in DB!"));
        } else {
            User admin = new User("admin", "tomaszekszym@gmail.com", "Tomasz", "Szymański", encoder.encode("admin"), "662527971");

            Set<Role> roles = new HashSet<>();
            Role adminRole = role_R.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Role modRole = role_R.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Role driverRole = role_R.findByName(ERole.ROLE_DRIVER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Role pakerRole = role_R.findByName(ERole.ROLE_PAKER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            roles.add(modRole);
            roles.add(driverRole);
            roles.add(pakerRole);
            Optional<City> cityOptional = city_R.findByName("Kielce");
            if (cityOptional.isPresent()){
                City city = cityOptional.get();
                admin.setLocalization(city.getId());
            }
            admin.setRoles(roles);
            admin.setDriver(true);
            user_R.save(admin);
        }
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Admin loaded !"));
    }

    @GetMapping("load_role")
    public String initiateRole() {
        role_R.deleteAll();

        Role role;
        var roles = ERole.values();
        for (ERole rolee : roles) {
            role = new Role(rolee);
            role_R.save(role);
        }
        StringBuilder out = new StringBuilder();
        for (Role roleE : role_R.findAll()) {
            out.append(roleE.getName());
            out.append("\n");
        }

        return "Baza ról załadowana\n" + out;
    }


    @GetMapping("load_province")
    public String initiateProvince() {
        prov_R.deleteAll();

        Province province;
        var prov = EProvince.values();
        for (EProvince prove : prov) {
            province = new Province(prove);
            prov_R.save(province);
        }

        StringBuilder out = new StringBuilder();
        for (Province provE : prov_R.findAll()) {
            out.append(provE.getName());
            out.append("\n");
        }

        return "Baza województw załadowana\n" + out;
    }

    @GetMapping("load_cartype")
    public String initiateCarTypes() {
        carType_R.deleteAll();

        CarType carType;
        var carTypes = ECarType.values();
        for (ECarType type : carTypes) {
            carType = new CarType(type);
            carType_R.save(carType);
        }
        StringBuilder out = new StringBuilder();
        for (Role roleE : role_R.findAll()) {
            out.append(roleE.getName());
            out.append("\n");
        }

        return "Baza typów aut załadowana\n" + out;
    }

    @GetMapping("load_cities")
    public String initiateCities() {
        city_R.deleteAll();

        List<City> cityList = new ArrayList<City>();
        City miasto1 = new City("Jelenia Góra", "58-500", prov_R.findByName(EProvince.PROV_DOLNOSLASKIE));
        City miasto2 = new City("Legnica", "59-220", prov_R.findByName(EProvince.PROV_DOLNOSLASKIE));
        City miasto3 = new City("Wałbrzych", "58-300", prov_R.findByName(EProvince.PROV_DOLNOSLASKIE));
        City miasto4 = new City("Wrocław", "50-031", prov_R.findByName(EProvince.PROV_DOLNOSLASKIE));
        City miasto5 = new City("Bydgoszcz", "85-708", prov_R.findByName(EProvince.PROV_KUJAWSKOPOMORSKIE));
        City miasto6 = new City("Grudziądz", "86-300", prov_R.findByName(EProvince.PROV_KUJAWSKOPOMORSKIE));
        City miasto7 = new City("Toruń", "87-100", prov_R.findByName(EProvince.PROV_KUJAWSKOPOMORSKIE));
        City miasto8 = new City("Włocławek", "87-800", prov_R.findByName(EProvince.PROV_KUJAWSKOPOMORSKIE));
        City miasto9 = new City("Biała Podlaska", "21-500", prov_R.findByName(EProvince.PROV_LUBELSKIE));
        City miasto10 = new City("Chełm", "22-100", prov_R.findByName(EProvince.PROV_LUBELSKIE));
        City miasto11 = new City("Lublin", "20-001", prov_R.findByName(EProvince.PROV_LUBELSKIE));
        City miasto12 = new City("Zamość", "22-400", prov_R.findByName(EProvince.PROV_LUBELSKIE));
        City miasto13 = new City("Gorzów Wielkopolski", "66-400", prov_R.findByName(EProvince.PROV_LUBUSKIE));
        City miasto14 = new City("Zielona Góra", "65-001", prov_R.findByName(EProvince.PROV_LUBUSKIE));
        City miasto15 = new City("Łódź", "90-001", prov_R.findByName(EProvince.PROV_LODZKIE));
        City miasto16 = new City("Piotrków Trybunalski", "97-300", prov_R.findByName(EProvince.PROV_LODZKIE));
        City miasto17 = new City("Skierniewice", "96-100", prov_R.findByName(EProvince.PROV_LODZKIE));
        City miasto18 = new City("Kraków", "30-001", prov_R.findByName(EProvince.PROV_MALOPOLSKIE));
        City miasto19 = new City("Nowy Sącz", "33-300", prov_R.findByName(EProvince.PROV_MALOPOLSKIE));
        City miasto20 = new City("Tarnów", "33-100", prov_R.findByName(EProvince.PROV_MALOPOLSKIE));
        City miasto21 = new City("Ostrołęka", "07-400", prov_R.findByName(EProvince.PROV_MAZOWIECKIE));
        City miasto22 = new City("Płock", "09-400", prov_R.findByName(EProvince.PROV_MAZOWIECKIE));
        City miasto23 = new City("Radom", "26-600", prov_R.findByName(EProvince.PROV_MAZOWIECKIE));
        City miasto24 = new City("Siedlce", "08-100", prov_R.findByName(EProvince.PROV_MAZOWIECKIE));
        City miasto25 = new City("Warszawa", "00-000", prov_R.findByName(EProvince.PROV_MAZOWIECKIE));
        City miasto26 = new City("Opole", "45-001", prov_R.findByName(EProvince.PROV_OPOLSKIE));
        City miasto27 = new City("Krosno", "38-400", prov_R.findByName(EProvince.PROV_PODKARPACKIE));
        City miasto28 = new City("Przemyśl", "37-700", prov_R.findByName(EProvince.PROV_PODKARPACKIE));
        City miasto29 = new City("Rzeszów", "35-001", prov_R.findByName(EProvince.PROV_PODKARPACKIE));
        City miasto30 = new City("Tarnobrzeg", "39-400", prov_R.findByName(EProvince.PROV_PODKARPACKIE));
        City miasto31 = new City("Białystok", "15-001", prov_R.findByName(EProvince.PROV_PODLASKIE));
        City miasto32 = new City("Łomża", "18-400", prov_R.findByName(EProvince.PROV_PODLASKIE));
        City miasto33 = new City("Suwałki", "16-400", prov_R.findByName(EProvince.PROV_PODLASKIE));
        City miasto34 = new City("Gdańsk", "80-001", prov_R.findByName(EProvince.PROV_POMORSKIE));
        City miasto35 = new City("Gdynia", "81-000", prov_R.findByName(EProvince.PROV_POMORSKIE));
        City miasto36 = new City("Słupsk", "76-200", prov_R.findByName(EProvince.PROV_POMORSKIE));
        City miasto37 = new City("Sopot", "81-701", prov_R.findByName(EProvince.PROV_POMORSKIE));
        City miasto38 = new City("Bielsko-Biała", "17-100", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto39 = new City("Bytom", "41-900", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto40 = new City("Chorzów", "41-500", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto41 = new City("Częstochowa", "42-200", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto42 = new City("Dąbrowa Górnicza", "41-300", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto43 = new City("Gliwice", "44-100", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto44 = new City("Jastrzębie-Zdrój", "43-251", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto45 = new City("Jaworzno", "43-600", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto46 = new City("Katowice", "40-001", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto47 = new City("Mysłowice", "41-400", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto48 = new City("Piekary Śląskie", "41-940", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto49 = new City("Ruda Śląska", "41-700", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto50 = new City("Rybnik", "44-200", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto51 = new City("Siemianowice Śląskie", "41-100", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto52 = new City("Sosnowiec", "41-200", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto53 = new City("Świętochłowice", "41-600", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto54 = new City("Tychy", "43-100", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto55 = new City("Zabrze", "41-800", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto56 = new City("Żory", "43-254", prov_R.findByName(EProvince.PROV_SLASKIE));
        City miasto57 = new City("Kielce", "25-000", prov_R.findByName(EProvince.PROV_SWIETOKRZYSKIE));
        City miasto58 = new City("Elbląg", "82-300", prov_R.findByName(EProvince.PROV_WARMINSKOMAZURSKIE));
        City miasto59 = new City("Olsztyn", "10-001", prov_R.findByName(EProvince.PROV_WARMINSKOMAZURSKIE));
        City miasto60 = new City("Kalisz", "62-800", prov_R.findByName(EProvince.PROV_WIELKOPOLSKIE));
        City miasto61 = new City("Konin", "62-500", prov_R.findByName(EProvince.PROV_WIELKOPOLSKIE));
        City miasto62 = new City("Leszno", "64-100", prov_R.findByName(EProvince.PROV_WIELKOPOLSKIE));
        City miasto63 = new City("Poznań", "60-001", prov_R.findByName(EProvince.PROV_WIELKOPOLSKIE));
        City miasto64 = new City("Szczecin", "70-001", prov_R.findByName(EProvince.PROV_ZACHODNIOPOMORSKIE));
        City miasto65 = new City("Koszalin", "75-001", prov_R.findByName(EProvince.PROV_ZACHODNIOPOMORSKIE));
        City miasto66 = new City("Świnoujście", "72-600", prov_R.findByName(EProvince.PROV_ZACHODNIOPOMORSKIE));





        cityList = new ArrayList<City>(Arrays.asList(
                miasto1, miasto2, miasto3,
                miasto4, miasto5, miasto6,
                miasto7, miasto8, miasto9,
                miasto10, miasto11, miasto12,
                miasto13, miasto14, miasto15,
                miasto16, miasto17, miasto18,
                miasto19, miasto20, miasto21,
                miasto22, miasto23, miasto24,
                miasto25, miasto26, miasto27,
                miasto28, miasto29, miasto30,
                miasto31, miasto32, miasto33,
                miasto34, miasto35, miasto36,
                miasto37, miasto38, miasto39,
                miasto40, miasto41, miasto42,
                miasto43, miasto44, miasto45,
                miasto46, miasto47, miasto48,
                miasto49, miasto50, miasto51,
                miasto52, miasto53, miasto54,
                miasto55, miasto56, miasto57,
                miasto58, miasto59, miasto60,
                miasto61, miasto62, miasto63,
                miasto64, miasto65, miasto66)) ;

        city_R.saveAll(cityList);


        miasto66.addNeighbours(miasto64, 106.0);// Swinoujscie = szczecin
        miasto66.addNeighbours(miasto65, 165.0);// Swinoujście = Koszalin

        miasto65.addNeighbours(miasto64, 171.0);// koszalin = szczecin
        miasto65.addNeighbours(miasto36, 68.2);// koszalin = słupsk

        miasto64.addNeighbours(miasto13, 110.0);// szczecin = gorzów wielkopolski

        miasto63.addNeighbours(miasto13, 137.0);// poznan = gorzów wielkopolski
        miasto63.addNeighbours(miasto14, 135.0);// poznań = zielona góra
        miasto63.addNeighbours(miasto62, 81.7);// poznan = leszno
        miasto63.addNeighbours(miasto5, 140.0);// poznan = bydgoszcz
        miasto63.addNeighbours(miasto61, 103.0);// poznan = konin

        miasto62.addNeighbours(miasto14, 110.0);// leszno = zielona góra
        miasto62.addNeighbours(miasto2, 99.1);// leszno = legnica
        miasto62.addNeighbours(miasto4, 99.6);// leszno = wrocław
        miasto62.addNeighbours(miasto60, 116.0);// leszno = kalisz

        miasto61.addNeighbours(miasto60, 55.2);// konin = kalisz
        miasto61.addNeighbours(miasto15, 127.0);// konin = łódź
        miasto61.addNeighbours(miasto8, 87.8);// konin = włocławek

        miasto60.addNeighbours(miasto4, 117.08);// kalisz = wrocław
        miasto60.addNeighbours(miasto15, 104.0);// kalisz = łodź

        miasto59.addNeighbours(miasto58, 110.0); //olsztyn = elblag
        miasto59.addNeighbours(miasto6, 138.0); //olsztyn = grudziadz
        miasto59.addNeighbours(miasto21, 126.0); //olsztyn = ostroleka
        miasto59.addNeighbours(miasto33, 198.0); //olsztyn = suwałki

        miasto58.addNeighbours(miasto6,105.0); //elblag = grudziadz
        miasto58.addNeighbours(miasto34,60.2); //elblag = gdansk

        miasto57.addNeighbours(miasto41,133.0); //kielce = czestochowa
        miasto57.addNeighbours(miasto18,127.0); //kielce = kraków
        miasto57.addNeighbours(miasto20,118.0); //kielce = tarnów
        miasto57.addNeighbours(miasto30,103.0); //kielce = tarnogrzeg
        miasto57.addNeighbours(miasto16,96.9); //kielce = tarnogrzeg


        miasto56.addNeighbours(miasto50,13.7); //żory = rybnik
        miasto56.addNeighbours(miasto44,16.2); //żory = jastrzębie-zdrój
        miasto56.addNeighbours(miasto46,36.3); //żory = Katowice
        miasto56.addNeighbours(miasto38,44.1); //żory = bielsko-biała

        miasto55.addNeighbours(miasto43,14.6); //zabrze = Gliwice
        miasto55.addNeighbours(miasto49,12.9); //zabrze = Ruda śląska
        miasto55.addNeighbours(miasto53,16.2); //zabrze = świętochłowice
        miasto55.addNeighbours(miasto39,11.2); //zabrze = bytom
        miasto55.addNeighbours(miasto48,13.8); //zabrze = piekary ślaskie
        miasto55.addNeighbours(miasto26,90.3); //zabrze = opole

        miasto54.addNeighbours(miasto38, 41.9);//Tychy = bielsko biała
        miasto54.addNeighbours(miasto47, 17.4);//Tychy = mysłowice
        miasto54.addNeighbours(miasto46, 31.1);//Tychy = katowice
        miasto54.addNeighbours(miasto49, 23.6);//Tychy = Ruda śląska
        miasto54.addNeighbours(miasto18, 82.4);//Tychy = kraków

        miasto53.addNeighbours(miasto39, 9.6); //świetochłowice = bytom
        miasto53.addNeighbours(miasto40, 10.3); //świetochłowice = chorzow
        miasto53.addNeighbours(miasto49, 9.3); //świetochłowice = ruda śląska
        miasto53.addNeighbours(miasto43, 19.1); //świetochłowice = gliwice

        miasto52.addNeighbours(miasto46, 6.6); //Sosnowiec =  katowice
        miasto52.addNeighbours(miasto51, 8.2); //Sosnowiec =  siemianowice ślaskie
        miasto52.addNeighbours(miasto42, 8.7); //Sosnowiec =  dabrowa gornicza
        miasto52.addNeighbours(miasto47, 17.8); //Sosnowiec =  myslowice
        miasto52.addNeighbours(miasto45, 19.3); //Sosnowiec =  jaworzno

        miasto51.addNeighbours(miasto48, 11.1); //siemianowice = piekary slaskie
        miasto51.addNeighbours(miasto39, 9.1); //siemianowice = bytom
        miasto51.addNeighbours(miasto40, 9.1); //siemianowice = chorzów
        miasto51.addNeighbours(miasto46, 9.2); //siemianowice = katowice
        miasto51.addNeighbours(miasto42, 12.5); //siemianowice = dabrowa gornicza
        miasto51.addNeighbours(miasto41, 67.9); //siemianowice = czestochowa

        miasto50.addNeighbours(miasto43, 25.3); // rybnik = gliwice
        miasto50.addNeighbours(miasto44, 21.9); // rybnik = jastrzevie zdroj

        miasto49.addNeighbours(miasto43, 15.3); // ruda ślaska = gliwice
        miasto49.addNeighbours(miasto40, 15.2); // ruda ślaska = chorzow
        miasto49.addNeighbours(miasto46, 13.9); // ruda ślaska = katowice

        miasto48.addNeighbours(miasto41, 60.0); // piekary slaskie = czestochowa
        miasto48.addNeighbours(miasto39, 5.1); // piekary slaskie = bytom

        miasto47.addNeighbours(miasto46, 13.6); //myslowice = katowiece
        miasto47.addNeighbours(miasto45, 16.6); //myslowice = jaworzno
        miasto47.addNeighbours(miasto42, 25.1); //myslowice = dabrowa gornicza

        miasto46.addNeighbours(miasto40, 6.4); // katowice = chorzow

        miasto45.addNeighbours(miasto42, 17.9); // jaworzno = dabrowa gornicza
        miasto45.addNeighbours(miasto38, 53.9); // jaworzno = bielsko biała
        miasto45.addNeighbours(miasto18, 60.7); // jaworzno = krakow

        miasto44.addNeighbours(miasto38, 55.1); // jastrzebie zdroj = bielsko biala

        miasto43.addNeighbours(miasto26, 81.6); // gliwice = opole

        miasto42.addNeighbours(miasto41, 65.6); // dabrowa gornicza = czestochowa
        miasto42.addNeighbours(miasto18, 70.0); // dabrowa gornicza = krakow

        miasto41.addNeighbours(miasto16, 82.3); // czestochowa = piotrkow trybunalski
        miasto41.addNeighbours(miasto18, 125.0); // czestochowa = krakow
        miasto41.addNeighbours(miasto26, 96.1); // czestochowa = opole

        //miasto40.addNeighbours(miasto38, 1.0); dodane wcześniej

        //miasto39.addNeighbours(miasto38, 1.0); dodane wcześniej

        miasto38.addNeighbours(miasto18, 85.1); //bielsko biała = kraków

        miasto37.addNeighbours(miasto35, 9.4); // sopot = gdynia
        miasto37.addNeighbours(miasto34, 12.2); // sopot = gdańsk

        miasto36.addNeighbours(miasto35, 111.0); // slupsk = gdynia

        //miasto35.addNeighbours(miasto38, 1.0);dodane wcześniej

        miasto34.addNeighbours(miasto6, 115.0); // gdańsk = grudziaz

        miasto33.addNeighbours(miasto31, 123.0); //suwałki = białystok
        miasto33.addNeighbours(miasto32, 139.0); //suwałki = łomza

        miasto32.addNeighbours(miasto21, 35.4); // łomża = ostrołeka
        miasto32.addNeighbours(miasto31, 83.2); // łomża = białystok
        miasto32.addNeighbours(miasto24, 133.0); // łomża = siedlce

        miasto31.addNeighbours(miasto24, 151.0); // białystok = siedlce

        miasto30.addNeighbours(miasto20, 92.1); // Tarnobrzeg = Tarnow
        miasto30.addNeighbours(miasto29, 73.0); // Tarnobrzeg = rzeszów
        miasto30.addNeighbours(miasto12, 138.0); // Tarnobrzeg = zamosc
        miasto30.addNeighbours(miasto11, 133.0); // Tarnobrzeg = lublin
        miasto30.addNeighbours(miasto23, 123.0); // Tarnobrzeg = radom

        miasto29.addNeighbours(miasto20, 86.4); // rzeszow = tarnow
        miasto29.addNeighbours(miasto27, 57.4); // rzeszow = krosno
        miasto29.addNeighbours(miasto28, 79.3); // rzeszow = przemysl
        miasto29.addNeighbours(miasto12, 151.0); // rzeszow = zamosc

        miasto28.addNeighbours(miasto27, 92.9); // przemysl = krosno
        miasto28.addNeighbours(miasto12, 139.0); // przemysl = zamosc

        miasto27.addNeighbours(miasto20, 76.2); // krosno = tarnow
        miasto27.addNeighbours(miasto19, 95.2); // krosno = nowy sacz

        miasto26.addNeighbours(miasto4, 86.3); //opole = wrocław

        miasto25.addNeighbours(miasto21, 121.0); // warszawa = ostroleka
        miasto25.addNeighbours(miasto24, 94.9); // warszawa = siedlce
        miasto25.addNeighbours(miasto23, 105.0); // warszawa = radom
        miasto25.addNeighbours(miasto17, 77.6); // warszawa = skierniewice
        miasto25.addNeighbours(miasto22, 111.0); // warszawa = plock

        miasto24.addNeighbours(miasto9, 69.4); // siedlce = biala podlaska
        miasto24.addNeighbours(miasto11, 125.0); // siedlce = lublin
        miasto24.addNeighbours(miasto23, 166.0); // siedlce = radom
        miasto24.addNeighbours(miasto21, 132.0); // siedlce = ostroleka

        miasto23.addNeighbours(miasto17, 106.0); // Radom = skierniewice
        miasto23.addNeighbours(miasto16, 109.0); // Radom = piotrkow tryb
        miasto23.addNeighbours(miasto11, 114.0); // Radom = lublin
        miasto23.addNeighbours(miasto9, 185.0); // Radom = biala-podlaska

        miasto22.addNeighbours(miasto7, 105.0); // plock = toruń
        miasto22.addNeighbours(miasto8, 51.2); // plock = wlocławek
        miasto22.addNeighbours(miasto17, 88.5); // plock = skierniewice
        miasto22.addNeighbours(miasto21, 158.0); // plock = ostroleka

        //miasto21.addNeighbours(miasto38, 1.0); dodane wczesnej

        miasto20.addNeighbours(miasto18, 94.3); // tarnow = krakow
        miasto20.addNeighbours(miasto19, 63.3); // tarnow = nowy sacz

        miasto19.addNeighbours(miasto18, 89.3); // nowy sacz =  krakow

        //miasto18.addNeighbours(miasto38, 1.0); dodane wcześniej

        miasto17.addNeighbours(miasto8, 121.0); // skierniewice = wlocławiek
        miasto17.addNeighbours(miasto15, 65.6); // skierniewice = lodz
        miasto17.addNeighbours(miasto16, 81.1); // skierniewice = piotrkow tryb

        miasto16.addNeighbours(miasto15, 47.1); // piotrkow tryb =  lodz

        miasto15.addNeighbours(miasto8, 120.0); // lodz = wloclawek
        miasto15.addNeighbours(miasto4, 216.0); // lodz = wroclaw

        miasto14.addNeighbours(miasto13, 113.0); // zielona gora = gorzow wielk
        miasto14.addNeighbours(miasto2, 105.0); // zielona gora = legnica

        miasto13.addNeighbours(miasto5, 210.0); // gorzow wielkoposlki =  bydgoszcz

        miasto12.addNeighbours(miasto10, 57.2); // zomosc = chelm
        miasto12.addNeighbours(miasto11, 88.1); // zomosc = lublin

        miasto11.addNeighbours(miasto9, 123.0); // lublin - biala podlaska
        miasto11.addNeighbours(miasto10, 70.2); // lublin - chełm

        miasto10.addNeighbours(miasto9, 124.0); // chelm =  biala podlaska

        //miasto9.addNeighbours(miasto38, 1.0); dodane wcześniej

        miasto8.addNeighbours(miasto7, 57.5); // wlocławek = toruń

        miasto7.addNeighbours(miasto5, 46.8); // toruń = bydgosz
        miasto7.addNeighbours(miasto6, 62.8); // toruń = grudziaz

        miasto6.addNeighbours(miasto5, 72.5); // grudziadz = bydgoszcz

        //miasto5.addNeighbours(miasto38, 1.0); wpisane wczesniej

        miasto4.addNeighbours(miasto2, 71.6); // wroclaw = legnica
        miasto4.addNeighbours(miasto3, 80.4); // wroclaw = walbrzych

        miasto3.addNeighbours(miasto2, 65.1); // walbrzych = legnica
        miasto3.addNeighbours(miasto1, 56.8); // walbrzych = jelenia gora

        miasto2.addNeighbours(miasto1, 55.9); // legnica = jelenia gora

        //miasto1.addNeighbours(miasto38, 1.0);dodane wcześniej


        city_R.saveAll(cityList);

        city_R.saveAll(cityList);

        StringBuilder out = new StringBuilder();
        for (City city : city_R.findAll()) {
            out.append(city.getName());
            out.append("\n");
        }

        return "Baza miast załadowana\n" + out;
    }

    @GetMapping("test1")
    public ResponseEntity<?> test1(){
        Optional<City> optionalCityOrigin = city_R.findByName("Bielsko-Biała");
        Optional<City> optionalCityDestiny = city_R.findByName("Gdynia");
        City origin = new City();
        City destiny= new City();
        if(optionalCityOrigin.isPresent()){
            origin = optionalCityOrigin.get();
        }
        if(optionalCityDestiny.isPresent()){
            destiny = optionalCityDestiny.get();
        }

        Route wynik = routeService.calculateRoute(origin , destiny);
        RouteResponse response = new RouteResponse();
        response.setDistance(wynik.getDistance());
        List<String> trasa = new ArrayList<>();
        for(City city : wynik.getRoute()) {
            trasa.add(city.getName());
        }
        trasa.add(destiny.getName());
        response.setId("test_1");
        response.setRoute(trasa);
        return ResponseEntity.ok(response);
    }

    @GetMapping("test2")
    public ResponseEntity<?> test2(){
        Optional<City> optionalCityOrigin = city_R.findByName("Gorzów Wielkopolski");
        Optional<City> optionalCityDestiny = city_R.findByName("Biała Podlaska");
        City origin = new City();
        City destiny= new City();
        if(optionalCityOrigin.isPresent()){
            origin = optionalCityOrigin.get();
        }
        if(optionalCityDestiny.isPresent()){
            destiny = optionalCityDestiny.get();
        }

        Route wynik = routeService.calculateRoute(origin , destiny);
        RouteResponse response = new RouteResponse();
        response.setDistance(wynik.getDistance());
        List<String> trasa = new ArrayList<>();
        for(City city : wynik.getRoute()) {
            trasa.add(city.getName());
        }
        trasa.add(destiny.getName());
        response.setId("test_2");
        response.setRoute(trasa);
        return ResponseEntity.ok(response);
    }
    @GetMapping("test3")
    public ResponseEntity<?> test3(){
        Optional<City> optionalCityOrigin = city_R.findByName("Jelenia Góra");
        Optional<City> optionalCityDestiny = city_R.findByName("Suwałki");
        City origin = new City();
        City destiny= new City();
        if(optionalCityOrigin.isPresent()){
            origin = optionalCityOrigin.get();
        }
        if(optionalCityDestiny.isPresent()){
            destiny = optionalCityDestiny.get();
        }

        Route wynik = routeService.calculateRoute(origin , destiny);
        RouteResponse response = new RouteResponse();
        response.setDistance(wynik.getDistance());
        List<String> trasa = new ArrayList<>();
        for(City city : wynik.getRoute()) {
            trasa.add(city.getName());
        }
        trasa.add(destiny.getName());
        response.setId("test_3");
        response.setRoute(trasa);
        return ResponseEntity.ok(response);
    }
    @GetMapping("test4")
    public ResponseEntity<?> test4(){
        Optional<City> optionalCityOrigin = city_R.findByName("Świnoujście");
        Optional<City> optionalCityDestiny = city_R.findByName("Przemyśl");
        City origin = new City();
        City destiny= new City();
        if(optionalCityOrigin.isPresent()){
            origin = optionalCityOrigin.get();
        }
        if(optionalCityDestiny.isPresent()){
            destiny = optionalCityDestiny.get();
        }

        Route wynik = routeService.calculateRoute(origin , destiny);
        RouteResponse response = new RouteResponse();
        response.setDistance(wynik.getDistance());
        List<String> trasa = new ArrayList<>();
        for(City city : wynik.getRoute()) {
            trasa.add(city.getName());
        }
        trasa.add(destiny.getName());
        response.setId("test_4");
        response.setRoute(trasa);
        return ResponseEntity.ok(response);
    }
    @GetMapping("test5")
    public ResponseEntity<?> test5(){
        Optional<City> optionalCityOrigin = city_R.findByName("Kraków");
        Optional<City> optionalCityDestiny = city_R.findByName("Opole");
        City origin = new City();
        City destiny= new City();
        if(optionalCityOrigin.isPresent()){
            origin = optionalCityOrigin.get();
        }
        if(optionalCityDestiny.isPresent()){
            destiny = optionalCityDestiny.get();
        }

        Route wynik = routeService.calculateRoute(origin , destiny);
        RouteResponse response = new RouteResponse();
        response.setDistance(wynik.getDistance());
        List<String> trasa = new ArrayList<>();
        for(City city : wynik.getRoute()) {
            trasa.add(city.getName());
        }
        trasa.add(destiny.getName());
        response.setId("test_5");
        response.setRoute(trasa);
        return ResponseEntity.ok(response);
    }

}
