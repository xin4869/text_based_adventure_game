package o1.missingFriend

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an “area” can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private val items = Map[String, Item]()

  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits

  def addItem(item: Item) = 
    items += item.name -> item
    item.setArea(this)   
    
  def addItems(items: Vector[Item]) = items.foreach(addItem)

  def contains(itemName:String): Boolean = items.contains(itemName)
  
  def getItem(itemName: String) : Option[Item] = items.get(itemName)
  def removeItem(itemName:String): Option[Item] =
    val item = items.remove(itemName)
    item

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. If there are no
    * items present, the return value has the form "DESCRIPTION\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". If there are one or more items present, the return
    * value has the form "DESCRIPTION\nYou see here: ITEMS SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The items and directions are listed in an arbitrary order. */
  def fullDescription: String =
    val intro = s"You are at ${this.name}. ${this.description}"
    val itemlist = if items.nonEmpty then "\nYou see here: " + items.keys.mkString(", ") else ""
    val exitList = "\n\nExits available:\n" + this.neighbors.map( (direction,area) => s"${direction}(${area})" ).mkString(", ")
    intro + itemlist + exitList

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name
  // this.name + ": " + this.description.replaceAll("\n", " ").take(150)

end Area

