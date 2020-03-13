import 'package:master_announcer/domain/announcement.dart';
import 'dart:async';
import 'package:sqflite/sqflite.dart';

class AnnouncementDatabase{

  Future<Database> database;
  final String dbName="announcement_database.db";
  final String table='announcements';
  final String toAdd='announcementsId';

  static final _database=AnnouncementDatabase._internal();

  factory AnnouncementDatabase(){
    return _database;
  }

  AnnouncementDatabase._internal(){
    database=openDatabase('announcement.db',onCreate:(db,version){
      db.execute("CREATE TABLE $table(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)");
      db.execute("CREATE TABLE $toAdd(id INTEGER PRIMARY KEY)");
    },version: 1);
  }

  Future<int> addAnnouncement(Announcement announcement) async{

    Database db=await database;
    return await db.insert(table, announcement.toMap(),conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<int> addAnnouncementId(Announcement announcement) async{
    Database db=await database;
    return await db.insert(table, announcement.toMapId(),conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<List<Announcement>> getAnnouncements() async{
    Database db=await database;
    List<Map<String,dynamic>> maps=await db.query(table);
    List<Announcement> announcements= List.generate(maps.length, (index){
      return Announcement(
        maps[index]['id'],
        maps[index]['title'],
        maps[index]['description']
      );
    });

    return announcements;
  }

  Future<void> updateAnnouncement(Announcement announcement)async{
    Database db=await database;

    await db.update(table, announcement.toMap(),where: "id=?",whereArgs: [announcement.id]);

  }

  Future<void> deleteAnnouncement(int id) async{
    Database db=await database;

    await db.delete(table,where: "id=?",whereArgs: [id]);

  }

  Future<void> deleteAll() async{
    Database db=await database;
    await db.delete(table);
  }

  Future<void> deleteToAdd() async{
    Database db=await database;
    await db.delete(toAdd);
  }

  Future<void> insertToAdd(int id)async{
    Database db=await database;
    await db.insert(toAdd, {"id":id},conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<List<int>> getToAdd()async{
    Database db=await database;
    List<Map<String,dynamic>> maps=await db.query(toAdd);
    List<int> announcements= List.generate(maps.length, (index)
    {
      return maps[index]['id'];
    });

    return announcements;
  }
}