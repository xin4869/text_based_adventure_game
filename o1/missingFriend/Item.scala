package o1.missingFriend
import scala.io.StdIn.*

/** The class `Item` represents items in a text adventure game. Each item has a name
  * and a longer description. (In later versions of the adventure game, items may
  * have other features as well.)
  *
  * N.B. It is assumed, but not enforced by this class, that items have unique names.
  * That is, no two items in a game world have the same name.
  *
  * @param name         the item’s name
  * @param description  the item’s description */
  
class Item(
  val name: String, val description: String, private val portable: Boolean = true, private var area: Option[Area] = None):

  private var subItems :Vector[Item] = Vector()
  def setArea(newArea: Area) = this.area = Some(newArea)
  def addSub(depItem: Item) = subItems = subItems :+ depItem
  def isPortable = portable
  def examined(): String = s"You took a close look of ${name}. ${description}\n"
  def interact(input:String): String = s"You try to move around ${name}. But nothing happens."
  override def toString = this.name
  
  protected def unlock() =
    area.foreach( a =>
      subItems.foreach(a.addItem(_))
      subItems = Vector()   
    )
   
  protected def sucess(subItem:Item, msg:String): String =
    var str = ""
    if subItem.name.toLowerCase.contains("page") then
      Diary.addPage(subItem)
      str = str + msg + s"\nYou've restored ${subItem.name} back to the diary."
    else
      area.foreach(_.addItem(subItem))
      str = str + msg + s"You've unlocked ${subItem.name}!"

    if subItems.nonEmpty then
      str= str + s"\nYou've also unlocked hidden items: ${subItems.map(_.name).mkString(", ")}."
      unlock()

    str


  protected def handleFloatInput(target:Vector[Float], msg: String, subItem: Item, input:String): String =
    input.toFloatOption match
      case Some(value) if target.contains(value) => sucess(subItem, msg)
      case None => "Incorrect number format!"
      case _ => s"You waited for a while, but nothing happens.."
      
  protected def handleTextInput(target:Vector[String], msg:String, subItem:Item, input:String): String =
    if target.exists(_.toLowerCase == input.toLowerCase) then sucess(subItem, msg)
    else "You waited for a while, but nothing happens..."


object Diary extends Item("diary","It's a torn out badly...many pages are missing...\n"):
  private var pages = Map[String, Item]()
  pages += "page1" -> new Item("page1", "More and more people are dying now... I'm so scared")
  def addPage(page:Item) = if !pages.contains(page.name) then pages += page.name -> page
  def countPages = pages.size
  override def interact(input:String) =
    "You are reading Alice's diary\n" +
      pages.map( (name, item) => s"${name}:${item.description}" ).mkString("\n")


class Painting(name:String, description: String, val angles:Vector[Float], val message:String, val item:Item)
  extends Item(name, description, portable=false):
  override def interact(input:String) =
    if input == "" then
      s"You found out the painting can be rotated by 0.1 to 9.9 degrees.\n" +
      s"You may rotate the painting using command in following format: interact ${name} 0.1"
    else
      handleFloatInput(angles,message,item,input)


class Desk(name:String, description:String, val codes:Vector[Float], val message:String, val item:Item)
  extends Item(name, description, portable=false):
  override def interact(input:String) =
    if input == "" then
      s"The drawer of the desk is locked by a 4-digit metal combination lock.\n"+
      s"You may try to unlock the drawer using command in following format: interact ${name} 000\""
    else
      handleFloatInput(codes,message,item,input)
      
class Desk2(name:String, description:String, val answers:Vector[String], val message:String, val item:Item)
  extends Item(name, description, portable=false):
  override def interact(input:String) =
    if input == "" then
      "You saw a sticker note that says: the answer lies in us.\n" +
       "hint:the answer is a string of arbitrary length, which may consist of any characters(number, string)\n" +
      s"You may try to unlock the drawer using command in following format: interact ${name} ___\""
    else
      handleTextInput(answers,message,item,input)
      

class Box(name:String, description:String, val codes:Vector[Float], val message:String, val item:Item)
  extends Item(name, description):
  override def interact(input:String) =
    if input == "" then
      s"You see a 4-digit lock\n" +
      s"You may try to unlock the wooden box using command in following format: interact ${name} 000\""
    else
      handleFloatInput(codes,message,item,input)

class Phone(name:String, description:String, val codes:Vector[Float], val message:String, val item:Item)
  extends Item(name, description):
  override def interact(input:String) =
      if input == "" then
        s"You turned on the phone, and you see that the password is a 6-digit number.\n"+
        s"You may use command in following format: interact ${name} 000\""
      else
        handleFloatInput(codes,message,item,input)
        
class Door(name:String, description:String, val codes:Vector[Float], val message:String, val item:Item)
  extends Item(name, description, portable=false):
  override def interact(input:String) =
      if input == "" then
        s"Aparently you need to input 6-digit to unlock the door.\n"+
        s"You may use command in following format: interact ${name} 000\""
      else
        handleFloatInput(codes,message,item,input)

class Chest(name:String, description:String, val codes:Vector[Float], val message:String, val item:Item)
  extends Item(name, description):
  override def interact(input:String) =
    if input == "" then
      s"You see a 3-digit lock\n" +
      s"You may try to unlock the wooden chest using command in following format: interact ${name} 000\""
    else
      handleFloatInput(codes,message,item,input)
        
class USB(name:String, description:String, val answers:Vector[String], val message:String, val item:Item)
  extends Item(name, description):
  override def interact(input:String) =
      if input == "" then
        s"You connect the USB to your laptop. It is clearly encrypted, and you'll need to unlock it.\n"+ 
        "A question pops up on the window:\n" +
        "Voiceless it cries, wingless vacillates, innocuous nibbles, mouthless mumbles\n"+
        s"You may guess what it is using command in following format: interact ${name} ___\""
      else
        handleTextInput(answers,message,item,input)
        
class ComputerA(name:String, description:String, val answers:Vector[String], val message:String, val item:Item)
  extends Item(name, description, portable=false):
  override def interact(input:String) =
      if input == "" then
        s"As you turn on the computer, a question pops up on the window:\n" +
        "I break without falling and start without an end.\n"+
        s"You may guess what it is using command in following format: interact ${name} ___\""
      else
        handleTextInput(answers,message,item,input)
        
class ComputerJ(name:String, description:String, val answers:Vector[String], val message:String, val item:Item)
  extends Item(name, description, portable=false):
  override def interact(input:String) =
      if input == "" then
        s"As you turn on the computer, a question pops up on the window:\n" +
        "I'm seen in the daylight, though I don't shine.\n" +
        "I'm present in the sky but have no form.\n"+
        s"You may guess what it is using command in following format: interact ${name} ___\""
      else
        handleTextInput(answers,message,item,input)
  
        