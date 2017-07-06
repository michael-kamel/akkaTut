import java.util.Optional;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AkkaBot extends AbstractActor
{
	  private Optional<Direction> direction = Optional.empty();
	  private boolean moving = false;
	  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	  public Receive createReceive() 
	  {
	      return receiveBuilder().match(Move.class, this::moveFilter, this::onMove).match(Stop.class, this::stopFilter, this::onStop).build();
	  }
	 
	  private boolean moveFilter(Move m)
	  {
		  return true;
	  }
	  private boolean stopFilter(Stop s)
	  {
		  return moving;
	  }
	  private void onMove(Move move) 
	  {
	      moving = true;
	      direction = Optional.of(move.direction);
	      log.info("I am now moving " + direction.get());
	  }
	  private void onStop(Stop stop) 
	  {
	      moving = false;
	      log.info("I stopped moving");
	  }

	public enum Direction { FORWARD, BACKWARDS, RIGHT, LEFT }
	public static class Move 
	{
	   public final Direction direction;
	   public Move(Direction direction) 
	   {
	       this.direction = direction;
	   }
	}
	public static class Stop {}
	
	public static class GetRobotState {}
	
	public static class RobotState 
	{
	   public final Direction direction;
	   public final boolean moving;
	   public RobotState(Direction direction, boolean moving)
	   {
	       this.direction = direction;
	       this.moving = moving;
	   }
	}
}