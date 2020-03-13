

class Announcement{

  int id;
  String title;
  String description;

  Announcement(int id,String title,String description){
    this.id=id;
    this.title=title;
    this.description=description;
  }

  @override
  String toString() {
    return "Announcement{ id:$id, title:$title, desc:$description}";
  }

  Map<String, dynamic> toMap(){
    return{
      "title": title,
      "description":description
    };
  }
  Map<String,dynamic> toMapId(){
    return{
      "id":id,
      "title": title,
      "description":description
    };
  }
}