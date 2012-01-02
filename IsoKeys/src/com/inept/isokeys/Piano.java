/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                         *
 *   IsoKeys, Copyright 2011 David A. Randolph                             *
 *                                                                         *
 *   FILE: Piano.java                                                      *
 *                                                                         *
 *   This file is part of IsoKeys, an open-source project                  *
 *   hosted at http://isokeys.sourceforge.net.                             *
 *                                                                         *
 *   IsoKeys is free software: you can redistribute it and/or              *
 *   modify it under the terms of the GNU General Public License           *
 *   as published by the Free Software Foundation, either version          *
 *   3 of the License, or (at your option) any later version.              *
 *                                                                         *
 *   IsoKeys is distributed in the hope that it will be useful,            *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with IsoKeys.  If not, see <http://www.gnu.org/licenses/>.      *
 *                                                                         *
 *   NOTE: The sounds used for this instrument were derived from           * 
 *   the acoustic-piano soundfont created by Roberto Gordo Saez.
 *   Here is its license, which we will display more prominently
 *   in the application and our web site, as soon as we get 
 *   organized:
 *   
Acoustic grand piano soundfont (Yamaha Disklavier Pro), release 2008-09-10
116 samples, 44100Hz, 16bit.

The acoustic grand piano soundfont is free. It is built from the Zenph
Studios Yamaha Disklavier Pro Piano Multisamples for OLPC.

The soundfont itself and all modifications made to the original
samples by Roberto Gordo Saez, published under a Creative Commons
Attribution 3.0 license.

Copyright 2008, Roberto Gordo Saez roberto.gordo@gmail.com Creative 
Commons Attribution 3.0 license http://creativecommons.org/licenses/by/3.0/

Zenph Studios Yamaha Disklavier Pro Piano Multisamples for OLPC:

A collection of Grand Piano samples played by a Yamaha Disklavier
Pro. Performed by computer and specifically recorded for OLPC by
Dr. Mikhail Krishtal, Director of Music Research and Production, and
his team at Zenph Studios. They are included in the OLPC sound
sample library.

How is it being done: "The Disklavier Pro has an internal
electronically-controlled mechanism that allows it to play sounds
with very precise specifications. It has its own file format known
as XP MIDI, an extension of standard midi. I Mikhail Krishtal
prepare the files for it to play -- in this case, representing notes
of different registers, durations, and dynamic levels."

http://csounds.com/olpc/pianoSamplesMikhail/pianoMikhail.html
Produced by Zenph Studios in Chapel Hill, North Carolina. The main
studio location is in Raleigh, North Carolina.

http://zenph.com/
Samples from the OLPC sound sample library:

This huge collection of new and original samples have been donated
to Dr. Richard Boulanger @ cSounds.com specifically to support the
OLPC developers, students, XO users, and computer and electronic
musicians everywhere. They are FREE and are offered under a CC-BY
license.

http://wiki.laptop.org/go/Sound_samples http://csounds.com/boulanger

Creative Commons Attribution 3.0 license
http://creativecommons.org/licenses/by/3.0/

* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.inept.isokeys;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Log;

public class Piano extends Instrument
{
	public Piano(Context context)
	{
		super(context);

		Pattern pat = Pattern.compile("^pno0*([0-9]+)v");
		Class raw = R.raw.class;
		Field[] fields = raw.getFields();
		for (Field field : fields)
		{
		    try
		    {
		    	String fieldName = field.getName();
		        if (fieldName.startsWith("pno", 0))
		        {
		        	int midiNoteNumber;
		        	Matcher mat = pat.matcher(fieldName);
		        	if (mat.find())
		        	{
		        		String midiNoteNumberStr = mat.group(1);
		        		midiNoteNumber = Integer.parseInt(midiNoteNumberStr);
		        		int fieldValue = field.getInt(null);	
		        		Log.d("Piano", midiNoteNumberStr + ": " + fieldValue);
		        		addSound(midiNoteNumber, fieldValue);
		        		mRootNotes.put(midiNoteNumber, midiNoteNumber);
		        		mRates.put(midiNoteNumber, 1.0f);
		        	}
		        }
		    }
		    catch(IllegalAccessException e) {
		        Log.e("REFLECTION", String.format("%s threw IllegalAccessException.",
		            field.getName()));
		    }
		}

		float previousRate = 1.0f;
		int previousRootNote = 21;
		for (int noteId = 21; noteId < 110; noteId++)
		{
			if (mRootNotes.containsKey(noteId))
			{
				previousRootNote = noteId;
				previousRate = 1.0f;
			}
			else
			{
				mRootNotes.put(noteId, previousRootNote);
				double oneTwelfth = 1.0/12.0;
			    double newRate = previousRate * Math.pow(2, oneTwelfth);	
			    previousRate = (float)newRate;
				mRates.put(noteId, previousRate);
			}
		}
//		addSound(21, R.raw.pno021v116leo);
//		addSound(24, R.raw.pno024v117leo);
//		addSound(27, R.raw.pno027v119leo);
//		addSound(30, R.raw.pno030v115leo);
//		addSound(33, R.raw.pno033v110leo);
//		addSound(36, R.raw.pno036v120leo);
//		addSound(39, R.raw.pno039v120leo);
//		addSound(42, R.raw.pno042v120leo);
//		addSound(45, R.raw.pno045v117leo);
//		addSound(48, R.raw.pno048v118leo);
//		addSound(51, R.raw.pno051v115leo);
//		addSound(54, R.raw.pno054v117leo);
//		addSound(57, R.raw.pno054v117leo);
//		addSound(57, R.raw.pno057v117leo);
//		addSound(60, R.raw.pno060v117leo);
//		addSound(63, R.raw.pno063v115leo);
//		addSound(66, R.raw.pno066v120leo);
//		addSound(69, R.raw.pno069v115leo);
//		addSound(72, R.raw.pno072v117leo);
//		addSound(75, R.raw.pno075v115leo);
//		addSound(78, R.raw.pno078v117leo);
//		addSound(81, R.raw.pno081v117leo);
//		addSound(84, R.raw.pno084v117leo);
//		addSound(87, R.raw.pno087v117leo);
//		addSound(90, R.raw.pno090v120leo);
//		addSound(93, R.raw.pno093v112leo);
//		addSound(96, R.raw.pno096v115leo);
//		addSound(96, R.raw.pno096v115leo);
//		addSound(102, R.raw.pno102v118leo);
	}
}