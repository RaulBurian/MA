import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:master_announcer/domain/announcement.dart';
import 'package:master_announcer/service/service.dart';

class AddEdit extends StatefulWidget {


  final Announcement announcement;
  final bool mode;

  const AddEdit(this.announcement,this.mode);

  @override
  State createState() {
    return AddEditState(announcement,mode);
  }
}

class AddEditState extends State<AddEdit>{
  Announcement announcement;
  bool edit;
  TextField titleText;
  TextField descText;
  TextEditingController titleController = TextEditingController();
  TextEditingController descController = TextEditingController();
  RaisedButton addEditButton;
  BuildContext context;
  State parent;

  AddEditState(Announcement announcement, bool mode) {
    this.announcement = announcement;
    this.edit = mode;
    titleText = TextField(
      controller: titleController,
    );
    descText = TextField(
      keyboardType: TextInputType.multiline,
      maxLines: 3,
      controller: descController,
    );
    titleController.text = announcement.title;
    descController.text = announcement.description;
    if (edit) {
      addEditButton = RaisedButton(
        onPressed: editAnnouncement,
        child: Text("Edit"),
      );
    } else {
      addEditButton = RaisedButton(
        onPressed: addAnnouncement,
        child: Text("Add"),
      );
    }
  }

  Widget _buildTiles(Announcement root) {
    return Scaffold(
        body: Container(
            alignment: Alignment.center,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[titleText, descText, addEditButton],
            )));
  }

  @override
  Widget build(BuildContext context) {
    this.context = context;
    return _buildTiles(announcement);
  }

  addAnnouncement() {

    Announcement a=Announcement(
        0, titleController.text, descController.text);
    Navigator.pop(context,a);
  }

  editAnnouncement() {

    Announcement a=Announcement(
        announcement.id, titleController.text, descController.text);

    print(a);
    Navigator.pop(context,a);
  }

  void f(){

  }
}

