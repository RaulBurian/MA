import sqlite3 from 'sqlite3';
import {Announcement} from "./announcement";

export class DBUtils{
    constructor(){
        this.db=new sqlite3.Database('announcements.db');
        this.db.run('CREATE TABLE IF NOT EXISTS announcements(' +
            'id INTEGER PRIMARY KEY AUTOINCREMENT,' +
            'title TEXT,' +
            'desc TEXT' +
            ')');
    }

    insert(announcement){
        return new Promise(resolve =>{
            this.db.run('INSERT INTO announcements (title,desc) VALUES (?,?)',
                [announcement.title,announcement.desc],(err)=>{
                resolve()
                });
        });

    }

    update(announcement){
        this.db.run('UPDATE announcements SET title=?, desc=? WHERE id=?',
            [announcement.title,announcement.desc,announcement.id]);
    }

    delete(id){
        this.db.run('DELETE FROM announcements WHERE id=?',[id]);
    }

    async getAnnouncements(){
        return new Promise(resolve=>{
            this.db.all('SELECT * FROM announcements',async (err,rows)=>{
                resolve(rows);
            });
        })
    }
}


