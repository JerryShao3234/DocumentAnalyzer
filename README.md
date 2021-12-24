## Document Analyzer  
This project is about analyzing text documents based on many metrics. Many famous algorithms, techniques, and third party APIs are implemented in Java to perform document analysis. Features such as 
text metrics, sentiment metrics, plagiarism checking, document encryption/decryption, data untangling, etc. are provided in a comprehensive way.  

### Part 1: Text and Linguistic Metrics  
The first part of this project aims to retrieve a specified text document and perform sentence and word level metrics. Basic statistics, like average sentence length, average sentence complexity (no. of phrases per sentence), average word length, and unique word ratio will be accumulated. These aim to quantify the complexity of a document.    
As a linguistics bonus, the Hapax Legomana ratio is also included.  
The implementation is highly versatile as both documents within the local file system and online webpages are valid inputs.  
Note that specifications regarding definitions for a valid "word" or "sentence" are strict and documented in the code to ensure valid results.  
  
The specification for a word is given as follows:  
- All text is converted to lowercase for the purpose of analysis.  
- **What is a word?** We start by defining the **tokens** in a document to be the `String`s that we obtain when we split the `String` that represents the complete text around whitespaces. A **word** is a non-empty token that is not completely made up of punctuation. If a token begins or ends with punctuation then a word can be obtained by removing the starting and trailing punctuation.
- Specifically, the start of word should not contain any of ! " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | }.  
- The end of a word should not include any of ! # " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~.
- A word beginning with a `#` can support hashtags as separate words.  
- Hyphenated words are considered to be one word (e.g., "real-time").  
- When reading text, on occasion, two words may be separated by a newline character (`\n`) and not other whitespace characters.  

The specification for a sentence is given as follows:  
  
A sentence is defined to be a sequence of characters that is terminated by the characters **! ? .** or the EOF, excludes all whitespace on either end, and is not empty.  



### Part 2: Sentiment Analysis  
Using the Google Cloud Natural Language API, the sentiment of each individual sentence in a document can be artifically quantified and extracted.  
This aims to determine the tone/sentiment of a text document and labels it as "positive" or "negative". Additionally, the implementation also directs users to the most positive and most negative sentence.  
**Specs, gradle stuff, and runtime stuff**  

### Part 3: Document Similarity and Plagiarism Checking  
Modelling a union-find algorithm with similarity metrics, multiple documents can be grouped based on their similarity.  
Similarity is determined using the **Jensen-Shannon Divergence** equation adapted for word and sentence statistics.  
The similarity metric aims to provide users with a series of similar documents and also provide plagariasm checking capability on multiple documents.  

### Part 4: Fourier Transform, Cryptanalysis, and Data Untangling  
This part of the project uses the Discrete Fourier Transform (DFT) to encrypt and decrypt text documents.  
A complex number class was created (along with all complex and real number operations) to facilitate the DFT equation. Text was converted character by character into an ASCII array, where the classic sinusoid equation was applied to each element to produce an encrypted array.  
A brute force decryption algorithm using the DFT was then applied to the array to retrieve the exact parameters of the sinusoidal formula to reverse the encryption and thus return the plain text.
Finally, to determine if a text document was actually the interleaving of two other signals, a recursive untangling algorithm was constructed to determine whether an input signal was an interleaving.  










