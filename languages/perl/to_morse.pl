#!/usr/bin/perl
use strict;
use warnings;

my $input = uc shift;
my $output = '';

for my $ch (split //, $input) {
    if(length $output > 0 ) {$output .= '_';}
    if($ch eq 'A') {$output .= '*-';}
    if($ch eq 'B') {$output .= '-***';}
    if($ch eq 'C') {$output .= '-*-*';}
    if($ch eq 'D') {$output .= '-**';}
    if($ch eq 'E') {$output .= '*';}
    if($ch eq 'F') {$output .= '**-*';}
    if($ch eq 'G') {$output .= '--*';}
    if($ch eq 'H') {$output .= '****';}
    if($ch eq 'I') {$output .= '**';}
    if($ch eq 'J') {$output .= '*---';}
    if($ch eq 'K') {$output .= '-*-';}
    if($ch eq 'L') {$output .= '*-**';}
    if($ch eq 'M') {$output .= '--';}
    if($ch eq 'N') {$output .= '-*';}
    if($ch eq 'O') {$output .= '---';}
    if($ch eq 'P') {$output .= '*--*';}
    if($ch eq 'Q') {$output .= '--*-';}
    if($ch eq 'R') {$output .= '*-*';}
    if($ch eq 'S') {$output .= '***';}
    if($ch eq 'T') {$output .= '-';}
    if($ch eq 'U') {$output .= '**-';}
    if($ch eq 'V') {$output .= '***-';}
    if($ch eq 'W') {$output .= '*--';}
    if($ch eq 'X') {$output .= '-**-';}
    if($ch eq 'Y') {$output .= '-*--';}
    if($ch eq 'Z') {$output .= '--**';}
    if($ch eq ' ') {$output .= '__';}
}

print $output;
