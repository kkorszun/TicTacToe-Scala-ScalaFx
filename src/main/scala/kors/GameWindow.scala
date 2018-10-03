package kors

import javafx.scene.control.{Label, TitledPane}
import kors.GameEngine.GameState._
import kors.GameEngine.GameSymbol.{CircleS, CrossS, EmptyS, GameSymbol}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData._
import scalafx.scene.control.{Alert, Button}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{ColumnConstraints, GridPane, HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text


object GameWindow extends JFXApp {

  var myGame = new GameEngine

  def resetGame = {myGame = new GameEngine; setGameStateToGrid}

  def closeGame = { stage.close }

  var titleText = new Text {
    text = "TicTacToe "
    style = "-fx-font-size: 20pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(Gray, WhiteSmoke)
    )
  }

  var footerWithBtts = new HBox{
    children = Seq(
      new Button{
        text = "new game"
        onAction = {_ => resetGame}
      },
      new Button{
       text =  "exit"
        onAction = {_ => closeGame}
      }
    )
  }

  def toStringSymbol: GameSymbol => String = {
    case EmptyS   =>  "  "
    case CrossS   =>  "╳"
    case CircleS  =>  "◯"
  }

  def TwoDimZipper[A, B](x :Array[Array[A]],y :Array[Array[B]]) =
    x.zip(y).map(_ match { case (x1,y1) => x1.zip(y1)})

  def setGameStateToGrid :Unit = {
    TwoDimZipper(myGame.getGameBoard, bttnArr).foreach(_.foreach(
      _ match  {
        case (x,y) => {
          y.text.value_=  { this toStringSymbol x }
          if(x == CrossS || x == CircleS) y.disable = true
          else y.disable = false
        }
      }
    ))
  }

  def showAlert(x :GameState) = {

    def myAlert(myMessage :String) = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "TicTacToe"
      contentText = myMessage+"New game?"

      //onHiding = { _ => {stage.close()}}
    }.showAndWait() match {
      case Some(x) => x.buttonData match {
        case CancelClose => closeGame
        case OKDone => resetGame
        case _ => {}
      }
      case None => {}
    }

    x match {
      case Draw => myAlert("draw.")
      case CrossV => myAlert("╳ wins. ")
      case CircleV => myAlert("◯ wins. ")
    }

  }

  def getMyButton(c :Int,  r:Int) = new Button{
    text = " "
    onAction = { value => {
      val result = myGame.nextMove(c,r)
      setGameStateToGrid
      if(result._1 != NonV) {
        bttnArr.flatten.foreach(_.disable = true)
        showAlert(result._1)
      }
    }}
  }

  def getButtonsArr = {
    var btt11 = getMyButton(0,0)
    var btt12 = getMyButton(0,1)
    var btt13 = getMyButton(0,2)
    var btt21 = getMyButton(1,0)
    var btt22 = getMyButton(1,1)
    var btt23 = getMyButton(1,2)
    var btt31 = getMyButton(2,0)
    var btt32 = getMyButton(2,1)
    var btt33 = getMyButton(2,2)

    Array(
      Array(btt11,btt12, btt13),
      Array(btt21, btt22, btt23),
      Array(btt31, btt32, btt33))
  }

  var gridPane = new GridPane()
  gridPane.setGridLinesVisible(true)

  val bttnArr: Array[Array[Button]] = getButtonsArr
  bttnArr.zipWithIndex.foreach({
    case (s,i) => s.zipWithIndex.foreach({
      case (s1, j) => gridPane.add(s1,j,i)
    })
  })


  setGameStateToGrid

  new Label()
  stage = new JFXApp.PrimaryStage {
    title.value = "TicTacToe"
    scene = new Scene {
      fill = Black
      content = new VBox{
        children = Seq(
          new HBox(titleText),
          gridPane,
          footerWithBtts
        )
      }
    }
  }

}
