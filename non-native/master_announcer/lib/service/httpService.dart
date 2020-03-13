import 'dart:convert';
import 'package:master_announcer/domain/announcement.dart';
import 'package:master_announcer/repository/repository.dart';
import 'package:http/http.dart';

class HttpService {
  static final HttpService _httpService = HttpService._internal();
  final String _baseUrl = 'http://10.0.2.2:3000/announcements';

  factory HttpService() {
    return _httpService;
  }

  HttpService._internal();

  Future<List<Announcement>> getAnnouncements() async {
    Response response = await get(_baseUrl);
    List<Announcement> announcements = List();
    if (response.statusCode == 200) {
      List<dynamic> maps = jsonDecode(response.body);
      announcements = List.generate(maps.length, (index) {
        return Announcement(maps[index]['id'] * (-1), maps[index]['title'],
            maps[index]['desc']);
      });
    }
    return announcements;
  }

  Future<void> addAnnouncement(Announcement announcement) async {
    Response response = await post(_baseUrl, body: {
      "title": announcement.title,
      "desc": announcement.description
    });
  }

  Future<void> deleteAnnouncement(int id) async{
    id=id*(-1);
    await delete('$_baseUrl?id=$id');
  }

  Future<void> editAnnouncement(Announcement announcement) async{
    await put(_baseUrl,body: {
      "id":(announcement.id*(-1)).toString(),
      "title":announcement.title,
      "desc":announcement.description
    });
  }
}
