package umtaxi.springserver.dto;

public class MatchDto {

    public int userId;
    public double startLatitude; //내 위치
    public double startLongitude;
    public double endLatitude; //가고 싶은데 위치
    public double endLongitude;
    public int limitTime;
}
