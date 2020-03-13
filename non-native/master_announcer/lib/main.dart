import 'package:flutter/material.dart';
import 'package:master_announcer/addedit.dart';
import 'package:master_announcer/domain/announcement.dart';
import 'package:master_announcer/service/httpService.dart';
import 'package:master_announcer/service/service.dart';
import 'package:connectivity/connectivity.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Master Announcer',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: MyHomePage(title: 'Master Announcer'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  MyHomePageState createState() => MyHomePageState();
}

class MyHomePageState extends State<MyHomePage> {
  Service service = Service();
  HttpService httpService=HttpService();

  Future<bool> checkNetwork() async {
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile) {
      return true;
    } else if (connectivityResult == ConnectivityResult.wifi) {
      return true;
    } else {
      return false;
    }
  }

  void deleteItem(int id) async {
    if (await checkNetwork()) {
      await httpService.deleteAnnouncement(id);
      service.deleteAnnouncement(id);
      setState(() {});
    } else {
      Fluttertoast.showToast(
          msg: "Network not connected!",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.BOTTOM);
    }
  }

  void addItem() async {

    Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) =>
                    AddEdit(Announcement(-5, "Title", "Description"), false)))
        .then((a) async {
      if (a != null) {
        int lastId = await service.addAnnouncement(a);

        setState(() {});
        if(await checkNetwork()){
          httpService.addAnnouncement(a);
        }
        else{
          Fluttertoast.showToast(
              msg: "Will be added when synced!",
              toastLength: Toast.LENGTH_SHORT,
              gravity: ToastGravity.BOTTOM);
          service.addToAdd(lastId);
        }
      }
    });
  }

  void editItem(Announcement announcement) async {
    if(await checkNetwork()){
      setState(() {
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => AddEdit(announcement, true))).then((a) async {
          if (a != null) {
            await httpService.editAnnouncement(a);
            service.editAnnouncement(a);
            setState(() {});

          }
        });
      });
    }else{
      Fluttertoast.showToast(
          msg: "Network not connected!",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.BOTTOM);
    }

  }

  void syncWithServer() async {
    if(await checkNetwork()){

      List<Announcement> announcementsDB=await service.getAnnouncements();
      List<int> announcementsToAdd=await service.getToAdd();
      print(announcementsDB);
      print(announcementsToAdd);
      for(int id in announcementsToAdd) {
        httpService.addAnnouncement(announcementsDB.firstWhere((announcement)=>announcement.id==id));
      }

      service.deleteAll();
      List<Announcement> announcements = await httpService.getAnnouncements();
      for(Announcement a in announcements) {
        await service.addAnnouncementId(a);
      }
      setState(() {

      });
    }else{
      Fluttertoast.showToast(
          msg: "Network not connected!",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.BOTTOM);
    }

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.sync),
            onPressed: () {
              syncWithServer();
            },
          )
        ],
      ),
      body: FutureBuilder<List<Announcement>>(
        future: service.getAnnouncements(),
        builder:
            (BuildContext context, AsyncSnapshot<List<Announcement>> snapshot) {
          if (snapshot.hasData) {
            return ListView.builder(
              itemCount: snapshot.data.length,
              itemBuilder: (BuildContext context, int index) => ListTile(
                title: Text(snapshot.data[index].title),
                onTap: () {
                  editItem(snapshot.data[index]);
                },
                trailing: IconButton(
                  icon: Icon(Icons.delete),
                  onPressed: () {
                    deleteItem(snapshot.data[index].id);
                  },
                ),
              ),
            );
          } else {
            return Center(child: CircularProgressIndicator());
          }
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: addItem,
        tooltip: 'Add',
        child: Icon(Icons.add),
      ),
    );
  }
}
