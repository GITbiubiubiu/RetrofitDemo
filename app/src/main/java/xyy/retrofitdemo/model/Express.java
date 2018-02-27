package xyy.retrofitdemo.model;

public class Express {

  private String time;
  private String context;
  private String location;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "Express{" +
        "time='" + time + '\'' +
        ", context='" + context + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}
