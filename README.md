# cljbash

An IRC quote database.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Migrations:

    lein -m cljbash.migrations

Importing existing quotes (assumes a fortune file with quotes separated by % on its own line):

    lein -m cljbash.import <filename>

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2013 FIXME
