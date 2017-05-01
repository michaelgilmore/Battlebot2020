#!/usr/bin/perl
use strict;
use warnings;

use Time::Local;

################################################################################################
# Hidden Message - Morse Code
################################################################################################

################################################################################################
# Step One
# Given a string that has been converted to Morse code, find all of the possible and unique sequences of
# remaining tokens after removing the second message from the first. There are 3 different types of
# tokens in the Morse code message.
# • Dot (*)
# • Dash (-)
# • Blank (_)
# Every letter in the message is separated by a single blank character and every word is separated by 3
# blank characters.
# Example:
# • Given: AB *-_-***
# • Remove: R *-*
# • This can be done 6 different ways.
# o X X _ - X * * o X X _ - * X * o X X _ - * * X
# o X - _ X X * * o X - _ X * X * o X - _ X * * X
# • But this results in only 2 possible unique sequences of remaining tokens.
# o _ - * * o - _ * *
# • So the final result for this example would be 2.
# 
# Write a program that can calculate all of the deletion paths for removing one Morse code message
# from another. This program should be able to calculate all of the paths in the example below in under
# 10 seconds and return the total number of paths found. This is a preliminary step for your program, for
# testing.
# • Given: Hello World
# • Remove: Help
# o ****_*_*-**_*- **_-- -___*- -_- -- _*-*_*- **_-**
# o ****_*_*-**_*- -*
# • ANSWER: 1311
# 
################################################################################################
# Step two
# Find the possible deletion paths where you must remove a second message from the remaining tokens
# in the original message after removing the first phrase, including blank tokens. All remaining tokens
# would be kept in the same order to find a second phrase. Return all of the possible and unique
# sequences of remaining tokens after removing both phrases.
# In the example above where R is removed from AB notice that there are 6 delete paths but only 2
# unique sequences of remaining tokens.
# Example:
# • Given: ABCD
# • Remove: ST
# • Then Remove: ZN
# • One solution path would look like:
# o *-_- ***_-*- *_-**
# o ***_-
# o -- **_-*
# o Start:
# o Remove ST:
# o Then Remove ZN:
# o The set of remaining characters
# ? * - _ - * * * _ - * - * _ - * *
# ? x - _ - x x * x x * - * _ - * *
# ? x x _ x x x x x x x - * x x x *
# ? _ - * *
# • There are 5 sequences of remaining characters for this example:
# o _-** o _*-* o -_** o *_-* o *-_*
# Expand your program to find all of the possible sequences of remaining characters after removing 2
# hidden Morse code messages from an original message. This program should be able to calculate all of
# the sequences in the example below in less than 60 seconds and return the total number of distinct and
# valid sequences found. This will be the final expected output from the program.
# • Given: The Star Wars Saga o -_****_*___***_- _*-_*- *___*-- _*-_*- *_***___***_*-_- -*_*-
# • Remove: Yoda o -*- -_- -- _-**_*-
# • And Remove: Leia o *-**_*_**_*-
# • Expected Answer: 11474
# 
# Program Specifications
# • Input: Three command-line arguments denoting the original message, the first hidden message,
# and the second hidden message. The three messages will be in Morse code using the
# representation described here.
# • Expected Output: Total number of distinct and valid remaining token sequences in the original
# message.
# • Bounds: Original Message less than 100 Morse code characters.

################################################################################################
# Test cases
# 1. No parameters
# 2. Only one parameter which is valid
# 3. Only one parameter which is invalid
# 4. Two parameters, neither is valid
# 5. Two parameters, first is valid, second is invalid
# 6. Two parameters, first is invalid, second is valid
# 7. Two parameters, both valid


my $usage = <<'END_USAGE';
this_script.pl [source] [hidden_msg]

source = a string of Morse Code made up only of dits, dahs, and spaces
hidden_msg = a string of Morse Code made up only of dits, dahs, and spaces that should be found within the source string
dit = the '*' character
dah = the '-' character
space = the '_' character

There should be a single space character between each Morse Code letter and three space characters between each Morse Code word.

Example:
this_script.pl ****_*_*-**_*-**_---___*-_-_****_*_-*_*-_****_*_*-_*-**_-_**** **-*_**_-*_-**___--_*
source = hello athenahealth
hidden_msg = find me
END_USAGE

my $start_time = timegm(gmtime());
print "Beginning Hidden String process...\n";

# Read in two strings
my $source = shift or exitWithErrorAndUsage("FATAL: You must pass in a string from which to find a hidden message.", 1);
my $hidden1;
my @results;

# Check for a test run
if($source eq '-test1') {
    $source = '*-_-***'; #AB
    $hidden1 = '*-*'; #R
    print "Test1\n";
}
else {
    $hidden1 = shift or exitWithErrorAndUsage("FATAL: You must pass in a hidden string to find within the source string.", 1);
}

# Validate input
$source =~ /\A[*-_]+\z/ or exitWithErrorAndUsage("FATAL: Your source string should only contain dits(*), dahs(-), or spaces.", 1);
$hidden1 =~ /\A[*-_]+\z/ or exitWithErrorAndUsage("FATAL: Your hidden string should only contain dits(*), dahs(-), or spaces.", 1);

# Process strings
my $count = countHiddenStrings($source, $hidden1);

# Output result
print "Result = $count\n";

my $run_time_seconds = (timegm(gmtime()) - $start_time);
printf("Finished Hidden String process (runtime = %02d:%02d)\n", ($run_time_seconds/60), ($run_time_seconds%60));


################################################################################################
# functions

sub exitWithErrorAndUsage {
    print shift."\n";
    print $usage;
    exit shift;
}

sub countHiddenStrings {
    my ($src, $hid, $ind_ref) = @_;
    
    my @indices;
    if($ind_ref) {
        @indices = @$ind_ref;
        if($indices[0] > (length $src) - (length $hid)) {
            print "first index reached limit\n";
            return 0;
        }
    }
    else {
        @indices = (0..((length $hid)-1));
    }
    #for debugging
    #print "starting with these indices:";
    #foreach(@indices) {print $_;}
    #print "\n";

    my @hid_array = split //, $hid;
    my $start_index = 0;
    my $leftover = '';
    my $src_index = 0;
    my $i_to_bump = ((length $hid)-1);
    
    my $end_of_src_no_message = 0;
    for my $i (0..(@hid_array-1)) {
        $src_index = index($src, $hid_array[$i], $indices[$i]);
        #print "index = $src_index for i = $i\n";
        if($src_index == -1) {
            if($i == 0) {return 0;}
            $i_to_bump = $i - 1;
            $end_of_src_no_message = 1;
            last;
        }
        
        $leftover .= substr($src, $start_index, $src_index-$start_index);
        if($indices[$i] != $src_index) {
            bumpIndices(\@indices, $i, $src_index);
        }
        $start_index = $src_index + 1;
    }
    #for debugging
    #print "indices:";
    #foreach(@indices) {print $_;}
    #print "\n";

    if($end_of_src_no_message == 1) {
        #print "path1\n";
        bumpIndices(\@indices, $i_to_bump, $indices[$i_to_bump]+1);
        return countHiddenStrings($src, $hid, \@indices);
    }
    else {
        $leftover .= substr($src, $start_index, (length $src) - $start_index + 1);
        if(!grep {$_ eq $leftover} @results) {
            #print "path2\n";
            push @results, $leftover;
            print "Pattern ".(scalar @results)." '$leftover' added\n" ;
            bumpIndices(\@indices, $i_to_bump, $indices[$i_to_bump]+1);
            return 1 + countHiddenStrings($src, $hid, \@indices);
        }
        else {
            #print "path3\n";
            #print "Pattern '$leftover' already there\n" ;
        }
    }
    
    bumpIndices(\@indices, $i_to_bump, $indices[$i_to_bump]+1);
    return countHiddenStrings($src, $hid, \@indices);
}

sub bumpIndices {
    my($ind_ref, $start_i, $start_value) = @_;
    foreach my $i ($start_i..((scalar @$ind_ref)-1)) {
        if($i == 0) {
            my $sec = (timegm(gmtime()) - $start_time);
            printf("%02d:%02d ", ($sec/60), ($sec%60));
            print "bumping $i from @$ind_ref[$i] to $start_value\n";
        }
        @$ind_ref[$i] = $start_value;
        $start_value += 1;
    }
}