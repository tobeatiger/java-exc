package com.example.mongoconnect;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@SpringBootApplication
public class MongoConnectApplication {
	public static void main(String[] args) {
		SpringApplication.run(MongoConnectApplication.class, args);
	}
}

@RestController
@RequestMapping("/mongo")
class MongoService {

	MongoClient mongo;
	MongoDatabase database;

	public MongoService () {
		this.mongo = new MongoClient( "localhost" , 27017 );
		this.database = this.mongo.getDatabase("mycms");
	}

	@RequestMapping("/dbs")
	public String getDbs () {
		String names = "";
		for (String name : this.mongo.listDatabaseNames()) {
			names += name + '\n';
		}
		return names;
	}

	@RequestMapping("/collections")
	public String getCollections () {
		String names = "";
		for (String name : this.database.listCollectionNames()) {
			names += name + '\n';
		}
		return names;
	}

	@RequestMapping("/posts")
	public String getPosts () {

		String result = "";

		MongoCollection<Document> collection = database.getCollection("posts");

		FindIterable<Document> iterDoc = collection.find();

		// Getting the iterator
		Iterator it = iterDoc.iterator();

		while (it.hasNext()) {
			Document doc = (Document) it.next();
			result += doc;
		}

		return result;
	}
}