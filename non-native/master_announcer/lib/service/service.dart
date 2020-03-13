

import 'package:master_announcer/domain/announcement.dart';
import 'package:master_announcer/repository/repository.dart';

class Service{

  static final Service _service=Service._internal();

  Repository repository;

  factory Service(){
    return _service;
  }

  Service._internal(){
    repository=Repository();
  }


  Future<int> addAnnouncement(Announcement a){
    return repository.addAnnouncement(a);
  }

  Future<int> addAnnouncementId(Announcement a){
    return repository.addAnnouncementId(a);
  }

  void deleteAnnouncement(int id){
    repository.deleteAnnouncement(id);
  }

  void editAnnouncement(Announcement a){
    repository.editAnnouncement(a);
  }


  Future<List<Announcement>> getAnnouncements() async {
    List<Announcement> a= await repository.getAnnouncements();
    return a;
  }

  void addToAdd(int id){
    repository.addToAdd(id);
  }

  void deleteAll(){
    repository.deleteAll();
  }

  Future<List<int>> getToAdd() {
    return repository.getToAdd();
}
}