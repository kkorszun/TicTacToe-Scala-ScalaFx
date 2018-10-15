package kors

class CpuWrapper(isTwoPlayers: Boolean) {
  val gameInstance = new GameEngine

  def cpuPlayer =
    (scala.util.Random.nextInt(3),scala.util.Random.nextInt(3))

  def nextMove(x:Int, y:Int) = {
     gameInstance.nextMove(x,y) match {
      case (nonV,true) if isTwoPlayers == false => {
        var buforMove = gameInstance.nextMove(cpuPlayer._1, cpuPlayer._2)
        while(buforMove._2 != true) {
          buforMove = gameInstance.nextMove(cpuPlayer._1, cpuPlayer._2)
        }
        buforMove
      }
      case x => x
    }
  }


}
