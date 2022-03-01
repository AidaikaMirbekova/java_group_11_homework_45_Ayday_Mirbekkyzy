package com.company.lesson45;


import java.util.ArrayList;
import java.util.List;

  public class Book {
        private String name;
        private String author;
        private String status;

        public Book(){}

        public Book(String name) {
            this(name, null, null);
        }

        public Book(String name, String author) {
            this(name, author, null);
        }

        public Book(String name, String author, String status) {
            this.name = name;
            this.author = author;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String state) {
            this.status = status;
        }

        public class BookData{
            private Book.BookData bookData = new Book.BookData();
            private List<Book> bookList = new ArrayList<>();

            public BookData getBookData() {
                return bookData;
            }

            public void setBookData(BookData bookData) {
                this.bookData = bookData;
            }

            public List<Book> getBookList() {
                return bookList;
            }

            public void setBookList(List<Book> bookList) {
                this.bookList = bookList;
            }

            public BookData() {
                bookList.add(new Book("Автостопом", "Дуглас Адамс",""));
                bookList.add(new Book("Гордость и предубеждение", "Джейн Остин",""));
                bookList.add(new Book("Джейн Эйр", "Шарлотта Бронте",""));
                bookList.add(new Book("Убить пересмешника", "Харпер Ли",""));
                bookList.add(new Book("Гарри Поттер и Кубок Огня", "Джоан Роулинг",""));
            }

        }
    }

