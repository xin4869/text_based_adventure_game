package o1.missingFriend
import scala.collection.mutable.Buffer


class Game:

  /** the name of the game */
  val title = "The missing friend"
/********************************************************* set up game layout *********************************************/
/** default area */
  private val apartment = Area("apartment", "It's Alice's apartment.\nYou feel something is off. Seems like this place has been searched in a hurry.")
  private val neighbor = Area("neighborhood", "You are in front of Alice's neighbor's door.\n")
  private val storage = Area("storage", "It's Alice's personal storage room\nLooks very messy. Many boxes are knocked over, and things are everywhere.\nYou noticed a locked wooden box in the corner")
  private val cafe  =
    Area("cafe", "It's where you and Alice first met.\n" +
    "Both of you have become friends with the owner, Jane\n" +
    "As you step into the café, the familiar strains of your favorite song, Chasing Stars by Alesso, greet you from the background.\n" +
    "Jane greets and gives you things Alice left for you two weeks ago\n" +
    "According to Jane, she saw Alice meeting a guy at the cafe, and Alice seemed to be very down.\n")
  private val workplace =
    Area("workplace", "It's the company where Alice works, called Streamify\n" +
    "You did some research online, and you found that Streamify is a live-streaming e-commerce company.\n" +
    "The company hires and trains many influencers to conduct live-stream sales\n" +
    "It sells a wide range of small electronic products, such as toothbrushes and heart rate monitors\n")
  //private val warehouse = Area("warehouse", "It's a locked warehouse, dark, abandoned\nYou noticed the gate has a 6-digits lock.")

/** set up layout */
  apartment.setNeighbors(Vector("south" -> neighbor, "west" -> storage, "east" -> cafe, "north" -> workplace))
  neighbor.setNeighbors(Vector("north" -> apartment))
  storage.setNeighbors(Vector("east" -> apartment))
  workplace.setNeighbors(Vector("south" -> apartment))
  cafe.setNeighbors(Vector("west" -> apartment))

  /*********************************************************  items ****************************************************/
  /**************** apartment****************/
  val page2 = Item("page2", "I could have avoided this..I am so sorry")
  val msg1 = "Crispy click sound!\nA secret compartment appears on the wall next to the painting!\nYou can see a page marked with 2 inside the compartment"
  val painting1 = Painting("painting1","It's an oil painting of sunbeam casting shadows on wall", Vector(7.5f), msg1, page2)

  val clock1 = Item("clock1", "It's an analog clock on wall\nTime stoped at 3:15. Seems to be broken")

  val msg2 = "You opened the lock!\nThen, you opened the drawer and found a diary.\nYou recognize its Alice's handwriting."
  val desk1 = Desk("desk1", "Seems to be Alice's working desk.\nYou noticed a locked drawer", Vector(6996f, 9669f),  msg2, Diary)

  desk1.addSub(painting1)
  desk1.addSub(clock1)

  val apartmentItems : Vector[Item] = Vector(
    desk1,
    Item("photo1","It's a torn photograph of Alice.\nYou recognize that this photo was taken on her 16th birthday party."),
    Item("book1","It's a book on shelf.\nYou see a sentence written on the first page: The beauty of words lies in palindrome."),
    Item("poetry1","In the quiet of reversal, I transform—what was once mine, now mirrors yours, as if we are one in the turning.")
  )
  apartment.addItems(apartmentItems)


  /****************** storage *******************/
  val info3 =
    "It's an old wooden chest with an intricate carving of a bird secured by a 4-digit lock\n" +
    "It's a beautiful bird with feathers in 5 different colors." +
    "The bird's wings spread across four distinct sections, each more detailed than the last.\n"
  val msg3 =
    "You've unlocked the box!\n" +
    "You found another page of diary marked with number 3\n"

  val page3 = Item("page3", "Greed is the most dangerous. It turns people into monsters")

  val box1 = Box("box1", info3, Vector(5678f), msg3, page3)

  val storageItems: Vector[Item] = Vector(
    box1,
    Item("photo2", "It's a group photograh of Alice.\n" +
      "You can see only faces of Alice and a young girl, as others'faces have been cut off."),
    Item("postcard1", "A postcard fell out from somewhere as you search through the storage.\n" +
      "The picture was a night sky filled with five stars,each brighter than the last\n" +
      "'Dear Alice, I'm enjoying my trip in Banff National Park.\n" +
      "I just want to say, you save my life.\n" +
      "Without you, I wouldn't have had the chance to experience this amazing view\n" +
      "I can't wait to hangout with you again!\n")
  )
  storage.addItems(storageItems)

  /************************ cafe *********************/

  val info4 =
    "It's an old, bulky phone with oversized buttons and a clunky design.\n" +
    "But its known for being incredibly secure\n" +
    "You noticed that the phone case was a gift you gave Alice in 2018.\n"
  val msg4 =
    "You've unlocked the phone!\n" +
    "You found many threatening messages\n" +
    "But the sender remain unknown.\n"
  val messages1 = Item("messages1",
    "'Hand it over before it's too late. You know the consequences.'\n" +
    "'Don't be naive! We know what you are planning. It's not going to work'\n" +
    "'Time is running up, Alice...'\n"
  )
  val phone1 = Phone("phone1", info4, Vector(032018f), msg4, messages1)

  val cafeItems: Vector[Item] = Vector(
    phone1,
    Item("book2", "It's a book titled 'Suicide and Mental Health' by Rudy Nydegger\n" +
      "As you open the book, you see a bookmark with a poem written on it\n"),
    Item("bookmark1", "'When the frost has melted, and the birds return to song, the earth begins to speak once more.'\n")
  )
  cafe.addItems(cafeItems)

 /************************ neighbor *********************/
  val info5 =
    "You see an image of an eclipse affixed to the door with a string of letters: era marker of the digital age\n" +
    "You noticed that the nameplate says 'James'.\n" +
    "and the door lock accepts 8 digits.\n"
  val msg5 =
    "You've unlocked the door!\n" +
    "This place seems to be vacant.. no furniture at all\n"
  val bedroom1 = Item("bedroom1",
    "As you enter the bedroom, you are surprised..\n" +
    "It's a wall of Alice's photos.. Alice has been stalked and surveillanced!\n" +
    "Who is this guy? Why would he do this? You feel scared..\n"+
    "You noticed a locked wooden box.\n" +
    "As you are about to leave, you noticed a serveillance camara in the room!\n" +
    "Are you being watched this whole time?!\n" //TODO change step count?
  )
  val door1 = Door("door1", info5, Vector(08212017f), msg5, bedroom1)

  val msg7 =  "You've uncloked the USB! You found a contract and some other files!\n"
  val file1 = Item("file1", "It's a scanned contract.\n" +
    "'Party A: James, Party B:'Streamify\n" +
    "Project: Eclipse Protocol\n" +
    "All parties are obligated to keep the project confidential\n" +
    "Date:1.3.2018\n")
  val usb1 = USB("usb1", "An ordinary USB", Vector("breeze"), msg7, file1)

  val info6 = "A locked wooden chest. Seems mysterious.\n" +
    "You noticed a stirng of letters: A moment suspended in the sky, just long enough to be remembered.\n" +
    "The chest is secured by a 3-digit lock.\n"
  val msg6 = "You've unlocked the chest and you found a USB!\n"
  val box2 = Chest("box2", info6, Vector(160f), msg6, usb1)

  val news1 = Item("news1", "It's a torn piece from a newspaper.\n" +
    "Mayor A was found dead in his apartment...The police have announced it to be a suicide.\n")
  val news2 = Item("news2", "It's a torn piece from a newspaper.\n" +
    "The CEO of retail company X was discovered dead, with police confirming it as a suicide.\n" +
    "Investigation revealed his long battle with depression, which is likely to be the cause...\n")
  val news3 = Item("news3", "The winner of the biggest algorithm competition X has be announced!..\n" +
    "According to experts, this invention has ground-breaking effects....\n" +
    "She passionately shared her vision of leveraging her groundbreaking algorithm" +
    "to help prevent suicides and support mental health:\n")

  val bottl1 = Item("bottle1", "It's a small glass bottle filled with 6 blue pills.")

  door1.addSub(box2)
  box2.addSub(news1)
  box2.addSub(news2)
  box2.addSub(news3)
  box2.addSub(bottl1)
  neighbor.addItem(door1)
  
   /************************ workplace *********************/
  val page4 = Item("page4", "I should have not trusted people so easilly. It all started from my mistake in 2017, and I need to end this mess.\n")
  val ssd = Item("ssd", "A strudy and heavy hard drive.")
  val msg9 = "You've unlocked the drawer! You found a hard drive and a page of diary marked with 4\n"
  val desk2 = Desk2("desk2", "A working desk with Alice's nameplate.\nYou found a drawer locked", Vector("chasingstars2016"), msg9, page4)
  desk2.addSub(ssd)

  val info8 = "Alice's working computer\nOn the lock screen, a widget displays the time: 08:52 PM, 2024.\nA wave of anxiety washes over you as you realize how late it has already become."
  val msg8 = "You've unlocked Alice's computer!\nYou noticed that there is a video on desktop.\n"
  val video1 = Item("video1", "\n'My dear friend, by the time you see this vide, something may have already happend to me.\n" +
    "I'm sorry for keeping so many secrets from you - It was too dangerour. The less you know, the better.\n" +
    "If anything goes wrong, it must has something to do with James and streamify.\n" +
    "Please, take the hard drive from the drawer-they are all after it. Keey it safe.\n" +
    "Do you remember when we first met and realized that we shared the same favorite song? What a nice coincidence! I've missed you so much.'\n")
  val computerA = ComputerA("computer1", info8, Vector("dawn"), msg8, video1)

  val file2 = Item("file2", "Project: Eclipse protocal\nStarting date: 01.03.2017\n" +
   "Participants are obligated to insert chip and take pills to maintain its functionality\n" +
   "All collected data must be used with consent from the project board.\n")
  val msg10 = "You've unlocked James' computer! You found a project plan on the desktop\n"
  val info9 = "A computer on a desk with James' nameplate\nYou see the screen saver is an image of a total solar eclipse event\n"
  val computerJ = ComputerJ("computer2", info9, Vector("sunbeam"), msg10, file2)

  workplace.addItem(desk2)
  workplace.addItem(computerA)
  workplace.addItem(computerJ)

  /********************************** game set up *********************************************/
  /** The character that the player controls in the game. */
  val player = Player(apartment)
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 400
  val pageLimit = 4

  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete: Boolean = player.has("diary") && Diary.countPages > 4 && player.has("ssd")

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage =
    "Your best friend, Alice, has mysteriously disappeared.\n" +
    "Before she went missing, the two of you had agreed to meet at your favorite park, but she never showed up.\n" +
    "You've known each other for 8 years, and this kind of thing has never happened before\n" +
    "Now, her phone is off, and no one has seen or heard from her.\n" +
    "As you investigate further, you discover cryptic messages, hidden secrets, " +
    "and a trial of clues that take you deep into Alice's private life.\n" +
    "Did she leave willingly? Find out what has happened!\n\n" +
    s"To win the game, you need to get ssd and diary. You also need to restore at least ${pageLimit} diary pages within ${timeLimit} steps.\n"+
    "Now, get started from command <hint>. Have fun!"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then
      "You've collected 5 diary pages! Well done!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up...Game over!"
    else  // game over due to player quitting
      "Quitter!"

  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")

end Game

