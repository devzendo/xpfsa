#!/bin/sh
TESTDIR=$1
if [ ! -d $TESTDIR ]; then
    echo Test data directory $TESTDIR does not exist
    exit 1
fi

echo populating test directory $TESTDIR
cd $TESTDIR
mkdir directory

echo "hello" > testfile
sleep 2
chmod 644 testfile

ln -s testfile link-to-testfile

# small tree
mkdir tree
echo "firstfile" >> tree/firstfile
echo "secondfile" >> tree/secondfile
mkdir tree/onedir
echo "onedir-firstfile" >> tree/onedir/onedir-firstfile
echo "onedir-secondfile" >> tree/onedir/onedir-secondfile
mkdir tree/twodir
echo "twodir-firstfile" >> tree/twodir/twodir-firstfile
echo "twodir-secondfile" >> tree/twodir/twodir-secondfile
mkdir tree/twodir/bottomdir
echo "twodir-bottomdir-firstfile" >> tree/twodir/bottomdir/twodir-bottomdir-firstfile
echo "twodir-bottomdir-secondfile" >> tree/twodir/bottomdir/twodir-bottomdir-secondfile

exit 0
