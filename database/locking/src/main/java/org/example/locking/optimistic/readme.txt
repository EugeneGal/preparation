Task: transfer 100 from one account to another using optimistic locking.

1) Run Postgresql in docker container:
docker run --name my-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=db -p 5432:5432 -d postgres
2) Through terminal connect to db:
docker exec -it my-postgres psql -U user -d db
3) Create table:
CREATE TABLE money (
                     id SERIAL PRIMARY KEY,
                     balance INT NOT NULL,
                     version INT NOT NULL DEFAULT 1
                   );
4) Insert two records into table:
insert into money values (1, 1000, 1);
insert into money values (2, 200, 1);
5) In terminal begin transaction (T1) with update of first account:
begin;
update money set balance=balance+100, version=version+1 where id=1 and version=1;
6) Run code, you will see that you are hanging on extracting money from first account
7) Commit T1 in terminal:
commit;
8) In code if isolation level READ COMMITTED, you will get extractCount=0 and thus will go to retry.
If isolation level is above than READ COMMITTED, you will get 'could not serialize access due to concurrent update' exception
and also will go to retry.
9) How to see default isolation level:
SHOW default_transaction_isolation;
10) How to commit transaction with another isolation level:
begin transaction isolation level repeatable read;
11) How to check rows:
select ctid, xmin, xmax, * from money;

ctid - physical location of the row in the table (used for fast lookup).
xmin - the transaction ID that inserted the row.
xmax - the transaction ID that deleted the row (or marked it for an update).