# US008 - Check-up of vehicles

## 4. Construction (Implementation)

### Class VehicleController 

```java
public Optional<List<Vehicle>> requestList() {
    Optional<List<Vehicle>> request;

    request = vehicleRepository.requestList();
    return request;
}
```
