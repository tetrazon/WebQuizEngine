Web quiz is represented as the REST service. Quizzez are stored in DB, To solve a quiz, the client sends the POST request to the server. The service returns a JSON with two fields: success (true or false) and feedback (just a string).

To create a new quiz, the client need to send a JSON as the request's body via POST to /api/quizzes. The JSON should contain the four fields: title (a string), text (a string), options (an array of strings) and answer (integer index of the correct option).

Here is a new JSON quiz as an example:

{ "title": "The Java Logo", "text": "What is depicted on the Java logo?", "options": ["Robot","Tea leaf","Cup of coffee","Bug"], "answer": 2 }

The server response is a JSON with four fields: id, title, text and options. Here is an example.

{ "id": 1, "title": "The Java Logo", "text": "What is depicted on the Java logo?", "options": ["Robot","Tea leaf","Cup of coffee","Bug"] }

To get a quiz by id, the client sends the GET request to /api/quizzes/{id}.

Here is a response example:

{ "id": 1, "title": "The Java Logo", "text": "What is depicted on the Java logo?", "options": ["Robot","Tea leaf","Cup of coffee","Bug"] }

To get all existing quizzes in the service, the client sends the GET request to /api/quizzes.

The response contains a JSON array of quizzes like the following:

[ { "id": 1, "title": "The Java Logo", "text": "What is depicted on the Java logo?", "options": ["Robot","Tea leaf","Cup of coffee","Bug"] }, { "id": 2, "title": "The Ultimate Question", "text": "What is the answer to the Ultimate Question of Life, the Universe and Everything?", "options": ["Everything goes right","42","2+2=4","11011100"] } ]

To solve the quiz, the client sends a POST request to /api/quizzes/{id}/solve and passes the answer parameter in the content. This parameter is the index of a chosen option from options array. As before, it starts from zero.

The service returns a JSON with two fields: success (true or false) and feedback (just a string). There are three possible responses.

If the passed answer is correct (e.g., POST to /api/quizzes/1/solve with content answer=2): {"success":true,"feedback":"Congratulations, you're right!"}

If the answer is incorrect (e.g., POST to /api/quizzes/1/solve with content answer=1): {"success":false,"feedback":"Wrong answer! Please, try again."} If the specified quiz does not exist, the server returns the 404 (Not found) status code.

The server port is 8889, the address - http://192.168.186.1