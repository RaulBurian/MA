import Router from 'koa-router';
import {DBUtils} from "./db";
import {Announcement} from "./announcement";
import sqlite3 from "sqlite3";

export const router = new Router();
const db = new DBUtils();

router.get('/announcements', async ctx => {
    const response = ctx.response;
    await db.getAnnouncements().then(result=>{
       response.body=result;
    });
    response.status = 200;
});

router.post('/announcements',async ctx => {
    const body = ctx.request.body;
    const announcement=new Announcement(body.title,body.desc);
    const response = ctx.response;
    await db.insert(announcement).then(async result=>{
        await db.getAnnouncements().then(result=>{
            response.body=result[result.length-1];
        })
    });
    response.status = 200;
});

router.put('/announcements',ctx=>{
   const body = ctx.request.body;
   db.update(body);
   const response = ctx.response;
   response.status = 200;
});

router.delete('/announcements',ctx=>{
   const id = parseInt(ctx.request.query.id);
   db.delete(id);
   const response = ctx.response;
   response.status = 200;
});
