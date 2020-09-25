package pl.szymanski.paker.services;

import io.jsonwebtoken.lang.Assert;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.szymanski.paker.models.Item;
import pl.szymanski.paker.models.enums.ECarType;
import pl.szymanski.paker.payload.request.CargoCheckType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoServiceTest {
    CargoService cargoService = new CargoService();
    CargoCheckType cargoList = new CargoCheckType();
    @Test
    @Name("Pusty ładunek")
    void checkType1() {
        List<Item> items = new ArrayList<>();
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_SMALL),result);
    }
    @Test
    @Name("Mały ładunek 100 elementów 10x10")
    void checkType2() {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i<100;i++){
            items.add(new Item("item" +i,10,10,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_SMALL),result);
    }
    @Test
    @Name("średni ładunek 101 elementów 10x10")
    void checkType3() {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i<101;i++){
            items.add(new Item("item" +i,10,10,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_MID),result);
    }
    @Test
    @Name("średni ładunek 98 elementów 15x20 plus 30x20")
    void checkType4() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item",30,20,10,1,1));
        for(int i = 0; i<98;i++){
            items.add(new Item("item" +i,15,20,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_MID),result);
    }
    @Test
    @Name("duży ładunek 99 elementów 15x20 plus 30x20")
    void checkType5() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item",30,20,10,1,1));
        for(int i = 0; i<99;i++){
            items.add(new Item("item" +i,15,20,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_BIG),result);
    }
    @Test
    @Name("duży ładunek 100 elementów 20x50")
    void checkType6() {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i<100;i++){
            items.add(new Item("item" +i,20,50,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_BIG),result);
    }
    @Test
    @Name("Specjalny ładunek 101 elementów 20x50")
    void checkType7() {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i<101;i++){
            items.add(new Item("item" +i,20,50,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok(ECarType.TYPE_SPECIAL),result);
    }
    @Test
    @Name("błędny ładunek 101 elementów 20x50")
    void checkType8() {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i<101;i++){
            items.add(new Item("item" +i,200,50,10,1,1));
        }
        cargoList.setList(items);
        ResponseEntity<?> result = cargoService.checkType(cargoList);
        assertEquals(ResponseEntity.ok("NO"),result);
    }
}