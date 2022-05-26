# xml-stream-testing

This repository shows how to parse large XML files with streams. It is focussed on the use-case where the XML file is
extremely large and does not fit into memory, but you do need information from this XML. It assumes you have a tool that
can view large files, like IntelliJ, and we assume you cannot edit the file (as is often the case in practise).

## Analyzing the file

Three different types of analysis can be done with this tool, by creating a file which contains the contents:

* All the tags, ordered by their occurrence count
* All the tags just as in the XML but:
    * without `<` and `>`
    * with one space per indentation level
    * without content of the tags
    * without empty tags
* Every tag with their full XPath in the XML

Especially the last file will be extremely large, probably larger than the input XML. Since we assume you can read large
files, this can still be helpful.

## Usage of repository

Tests are available, which show exactly how to use this repository.

## Code structure

I have created a `TagReader`, which creates a stream of tags in the XML. This is done with creating a tag iterator based
on the character iterator which is based on an input stream of a file. Using this final iterator, it is converted into a
stream, which closes the reader when the stream is finished.

It took some time to get to this construction, but it works like a charm. At no point in time do we read the full file,
but we are able to convert the input stream (which can only read characters) into a stream of tags. The idea behind this
construction can be used for other practises as well. Hopefully this code gives you inspiration for your own projects!
