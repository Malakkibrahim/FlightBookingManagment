public class FlightSeat {
  public SeatClass seatClass;
  public double price;
  public int available;
  public int booked;

  public FlightSeat(SeatClass seatClass, double price, int available, int booked)
  {
    this.seatClass = seatClass;
    this.price = price;
    this.available = available;
    this.booked =  booked; 
  }

  public void book()
  {
    this.available -= 1;
    this.booked += 1;
  }  

  public void cancel() 
  {
    this.available += 1;
    this.booked -= 1;
  }

  @Override
  public String toString() {
    return seatClass + "," + price + "," + available + "," + booked;
  }
}
