package o1.missingFriend

/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val parts = commandText.split("\\s+")
  private val verb = parts.head
  private val noun = if parts.length > 1 then parts(1) else ""
  private val value = if (parts.length > 2) then Some(parts(2)) else None

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player): Option[String] =
      this.verb match
        case "go" => Some(actor.go(this.noun))
        case "quit" => Some(actor.quit())
        case "take" => Some(actor.take(this.noun))
        case "drop" => Some(actor.drop(this.noun))
        case "examine" => Some(actor.examine(this.noun))
        case "inventory" => Some(actor.inventory)
        case "interact" => Some(actor.interact(this.noun, this.value.getOrElse("")))
        case "action" => Some(actor.action)
        case "hint" => Some(actor.hint)
        case  other => None


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (noun: $noun)"

end Action

