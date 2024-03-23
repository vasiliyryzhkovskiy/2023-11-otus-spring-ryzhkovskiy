--insert into authors(full_name)
--values ('Александр Сергеевич Пушкин'), ('Лев Николаевич Толстой'), ('Виктор Олегович Пелевин');
--
--insert into genres(name)
--values ('рассказ'), ('роман'), ('сатира');
--
--insert into books(title, author_id, genre_id)
--values ('Метель', 1, 1), ('Война и мир', 2, 2), ('Чапаев и Пустота', 3, 3);
--
--insert into Comments(context, book_id)
--values ('Хороший рассказ', 1), ('Скучно', 2), ('Интересно', 3);

insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(text, book_id)
values ('Comment_1_1', 1), ('Comment_1_2', 1), ('Comment_2_1', 2), ('Comment_2_2', 2),  ('Comment_3_1', 3), ('Comment_3_2', 3);
