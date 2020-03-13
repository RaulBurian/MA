import Koa from 'koa';
import Router from 'koa-router';
import bodyParser from "koa-bodyparser";
import cors from '@koa/cors';
import {router} from "./router";

const timingLogger = async (ctx, next) => {
    const start = Date.now();
    await next();
    console.log(`${ctx.method} ${ctx.url} => ${ctx.response.status}, ${Date.now() - start}ms`);
};

const server=new Koa();


server.use(cors());
server.use(timingLogger);
server.use(bodyParser());
server.use(router.routes());

server.listen(3000);
console.log('Server started!');
