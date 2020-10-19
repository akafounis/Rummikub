package game;

class Trace {
  private TraceMove command;
  private Coordinate sourcePosition;
  private Coordinate targetPosition;

  Trace(TraceMove command, Coordinate sourcePosition, Coordinate targetPosition) {
    this.command = command;
    this.sourcePosition = sourcePosition;
    this.targetPosition = targetPosition;
  }

  TraceMove getCommand() {
    return command;
  }

  Coordinate getInitialPosition() {
    return sourcePosition;
  }

  Coordinate getTargetPosition() {
    return targetPosition;
  }

}
