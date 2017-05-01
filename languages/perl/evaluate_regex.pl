#!/usr/bin/perl
use strict;
use warnings;

print "begin\n";

my $src = "*-_-***";
#my @matches = ($src =~ /[.]*\*[.]*-[.]*\*[.]*/g);
#my @matches = ($src =~ /[.]*[*]{1}[.]*[-]{1}[.]*[*]{1}[.]*/g);
my @matches = ($src =~ /[*-_]*\*[*-_]*/g);

foreach my $match (@matches) {
    print "($match)\n";
}

print "end\n";
