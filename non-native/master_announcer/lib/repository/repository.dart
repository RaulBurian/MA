
import 'package:master_announcer/domain/announcement.dart';
import 'package:master_announcer/repository/database.dart';
import 'dart:async';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';


class Repository{

    AnnouncementDatabase announcementDatabase;

    static final _repository=Repository._internal();

    Repository._internal(){
      announcementDatabase=AnnouncementDatabase();
    }

    factory Repository(){
      return _repository;
    }

   Future<int> addAnnouncement(Announcement a) async {

      return await announcementDatabase.addAnnouncement(a);
    }

    Future<int> addAnnouncementId(Announcement a) async {

      return await announcementDatabase.addAnnouncementId(a);
    }

    Future<List<Announcement>> getAnnouncements() async {
      List<Announcement> a=await announcementDatabase.getAnnouncements();
      return a;
    }


    Future deleteAnnouncement(int id) async {
      await announcementDatabase.deleteAnnouncement(id);
    }

    Future editAnnouncement(Announcement newAnnouncement) async {
      await announcementDatabase.updateAnnouncement(newAnnouncement);
    }

    Future addToAdd(int id)async{
      await announcementDatabase.insertToAdd(id);
    }

    Future deleteAll()async{
      await announcementDatabase.deleteAll();
      await announcementDatabase.deleteToAdd();
    }

    Future<List<int>> getToAdd() async{
      return await announcementDatabase.getToAdd();
    }
}