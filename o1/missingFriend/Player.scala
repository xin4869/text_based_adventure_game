package o1.missingFriend

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  
  private var examinedItem: Option[Item] = None
  private val possessions = Map[String, Item]()
  private val maxItem = 10

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation
  

  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */


  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then s"You go $direction." else s"You can't go $direction."


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() =
    "You rest for a while. Better get a move on, though."

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""
  def has(itemName:String): Boolean = possessions.contains(itemName)

  def inventory: String =
    val str = if possessions.nonEmpty then
      "You are carrying:\n" + possessions.keys.mkString("\n")
    else
      "You are empty-handed"
    str

  def drop(itemName: String): String =
    possessions.remove(itemName) match
      case Some(item) =>
        location.addItem(item)
        s"You drop the ${item.name}."
      case None =>
        s"You don't have that!"


  private def isReachable(itemName:String) : Boolean =
  if has(itemName) then true
  else if location.contains(itemName) then true
  else false

  def get(itemName: String): Option[Item] = possessions.get(itemName)

  def action : String = "Available commands: " +
    "go, quit, take, drop, examine, inventory, interact, hint, action, rule\n" +
    "E.g., examine desk, hint, interact book"

  def hint : String = "You will need to unclock items to progress the game.\n" +
    "You can use commond <action> to get all valid action commands in the game\n" +
    "Try what you can do with each item using command <interact>\n" +
    "You might want to gather up all the clue available to you first"

  def take(itemName:String):String =
    if possessions.size >= maxItem then
      s"You have reached carrying limit:${maxItem} items."
    else if isReachable(itemName) then
      if possessions.contains(itemName) then s"The item ${itemName} is already in your pocket!"
      else
        val item = location.getItem(itemName)
        if !item.exists(_.isPortable) then s"You cannot pick up ${itemName} as its not portable"
        else
          item.foreach(possessions += itemName ->_)
          s"You pick up the ${itemName}."
    else
      s"This item is not within your reach at this point."


  def examine(itemName:String):String =
    if isReachable(itemName) then
      if possessions.contains(itemName) then
        val item = possessions(itemName)
        item.examined()
      else
        val item = location.getItem(itemName)
        item.map(_.examined()).getOrElse("")
    else
      s"This item is not within your reach at this point."


  def interact(itemName: String, input:String): String =
    if isReachable(itemName) then
      if has(itemName) then
        possessions(itemName).interact(input)
      else
        location.getItem(itemName).map(_.interact(input)).getOrElse("")
    else
      s"This item is not within your reach at this point."


  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name


