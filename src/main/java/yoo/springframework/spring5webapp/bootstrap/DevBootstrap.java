package yoo.springframework.spring5webapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import yoo.springframework.spring5webapp.model.Author;
import yoo.springframework.spring5webapp.model.Book;
import yoo.springframework.spring5webapp.model.Publisher;
import yoo.springframework.spring5webapp.repositories.AuthorRepository;
import yoo.springframework.spring5webapp.repositories.BookRepository;
import yoo.springframework.spring5webapp.repositories.PublisherRepository;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    //DI by constructor


    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    private void initData(){

//        Publisher publisher = new Publisher();
//        publisher.setName("newPub");
//
//        publisherRepository.save(publisher);



        Author eric = new Author("Eric", "Evans");
        Publisher hc = new Publisher("Harper Collins", "New York");

        Book ddd = new Book("Domain Driven Design", "1234", hc);
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        //post data to db
        //publisherRepository must be before bookRepository
        //as book db needs to have publisher id
        authorRepository.save(eric);
        publisherRepository.save(hc);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Johnson");
        Publisher work = new Publisher("Work", "New Mexico");
        Book noEJB = new Book("J2EE Development without EJB", "23444", work);
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        authorRepository.save(rod);
        publisherRepository.save(work);
        bookRepository.save(noEJB);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
