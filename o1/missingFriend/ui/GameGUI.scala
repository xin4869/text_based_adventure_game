package o1.missingFriend.ui

import scala.swing.*
import scala.swing.event.*
import javax.swing.UIManager
import o1.missingFriend.Game
import java.awt.{Point, Insets, Dimension}
import scala.language.adhocExtensions // enable extension of Swing classes
import java.awt.Font
import java.io.File

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it’s not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

/** The singleton object `AdventureGUI` represents a GUI-based version of the Adventure
  * game application. The object serves as a possible entry point for the game app, and can
  * be run to start up a user interface that operates in a separate window. The GUI reads
  * its input from a text field and displays information about the game world in uneditable
  * text areas.
  *
  * **NOTE TO STUDENTS: In this course, you don’t need to understand how this object works
  * on the inside. It’s enough to know that you can use this file to start the program.**
  *
  * @see [[AdventureTextUI]] */
object GameGUI extends SimpleSwingApplication:
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

   val fontSize = 18
    val myFont =
      try
        Font.createFont(Font.TRUETYPE_FONT, new File("doc/fonts/Inter-Regular.ttf")).deriveFont(fontSize)
      catch
        case ex:Exception =>
          println(s"Error loading customized font: ${ex.getMessage}")
          Font("SansSerif", Font.PLAIN, fontSize)

  val input = new TextField(40):
    minimumSize = preferredSize
    font = myFont

  def top = new MainFrame:

    // Access to the application’s internal logic:
    val game = Game()
    val player = game.player

    // Components:

    val locationInfo = new TextArea(7, 80):
      editable = false
      wordWrap = true
      lineWrap = true
      font = myFont
    val turnOutput = new TextArea(7, 80):
      editable = false
      wordWrap = true
      lineWrap = true
      font = myFont

    val turnCounter = Label()

    // Events:
    this.listenTo(input.keys)
    this.reactions += {
      case keyEvent: KeyPressed =>
        if keyEvent.source == input && keyEvent.key == Key.Enter && !this.game.isOver then
          val command = input.text.trim
          if command.nonEmpty then
            input.text = ""
            this.playTurn(command)
    }

    // Layout:

    this.contents = new GridBagPanel:
      import scala.swing.GridBagPanel.Anchor.*
      import scala.swing.GridBagPanel.Fill
      layout += Label("Location:") -> Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Command:")  -> Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Events:")   -> Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter        -> Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += locationInfo       -> Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += input              -> Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, Insets(5, 5, 5, 5), 0, 0)
      layout += turnOutput         -> Constraints(1, 2, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)

    // Menu:
    this.menuBar = new MenuBar:
      contents += new Menu("Program"):
        val quitAction = Action("Quit")( dispose() )
        contents += MenuItem(quitAction)

    // Set up the GUI’s initial state:
    this.title = game.title
    this.updateInfo(this.game.welcomeMessage)
    this.location = Point(900, 500)
    this.minimumSize = new Dimension(1200, 900)
    this.size = new Dimension(1600, 1200)
    this.pack()
    input.requestFocusInWindow()


    def playTurn(command: String) =
      val turnReport = this.game.playTurn(command)
      if this.player.hasQuit then
        this.dispose()
      else
        this.updateInfo(turnReport)
        input.enabled = !this.game.isOver


    def updateInfo(info: String) =
      if !this.game.isOver then
        this.turnOutput.text = info
      else
        this.turnOutput.text = info + "\n\n" + this.game.goodbyeMessage
      this.locationInfo.text = this.player.location.fullDescription
      this.turnCounter.text = "Turns played: " + this.game.turnCount

  end top

  // Enable this code to work even under the -language:strictEquality compiler option:
  private given CanEqual[Component, Component] = CanEqual.derived
  private given CanEqual[Key.Value, Key.Value] = CanEqual.derived

end GameGUI

