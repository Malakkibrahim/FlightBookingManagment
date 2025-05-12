import java.time.LocalDateTime;

public class DomesticFlight extends Flight {
  private String region;

  public DomesticFlight(String region, String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime,
    int availableEconomySeats, int bookedEconomySeats, double economyPrice, 
    int availableBusinessSeats, int bookedBusinessSeats, double businessPrice,
    int availableFirstClassSeats,  int bookedFirstClassSeats,  double firstClassPrice) {
    super(airline, origin, destination, departureTime, arrivalTime,availableEconomySeats, bookedEconomySeats,
        economyPrice, availableBusinessSeats, bookedBusinessSeats, businessPrice, availableFirstClassSeats, bookedFirstClassSeats, firstClassPrice);
    this.region = region;
  }

  @Override
  public String toString() {
    return super.toString() + "," + this.region;
  }
}
