﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Network.Packets;
using Network.Utils;

namespace Network
{
    public class ProtoBuffPacket
    {
        /// <summary>
        /// The username (UIN)
        /// </summary>
        /// <autogeneratedoc />
        public string Username;

        /// <summary>
        /// The payload
        /// </summary>
        [Obsolete("These are messages unparsed. Use Messages instead.")]
        public byte[] Payload;

        public List<Up_UpMsg> Messages;

        //DecodeMessage($input)
        public ProtoBuffPacket(byte[] data)
        {
            byte[] decryptedMsg;
            using (var ms = new MemoryStream(data))
            {
                using (var reader = new BinaryReader(ms))
                {
                    reader.ReadInt32(); // __xvtemp1 [UNUSED]
                    reader.ReadInt32(); // __xvtemp2 [UNUSED]

                    var encryptedCodeLength = reader.ReadInt32();

                    if (encryptedCodeLength > 0x2fffffff || encryptedCodeLength < 0 ||
                        (int)reader.BaseStream.Length - (int)reader.BaseStream.Position < encryptedCodeLength)
                        throw new Exception("Packet to short or long");

                    reader.ReadByte(); // mode / updateMem [UNUSED]
                    var count1 = reader.ReadByte();
                    var count2 = reader.ReadByte();
                    var uinLen = count2 * 256 + count1;

                    var uin = reader.ReadBytes(uinLen);
                    Username = Encoding.UTF8.GetString(uin);

                    //$tbUserInfo = PlayerCacheModule::getUserInfoData($uin, $mode);

                    var cryptCodeLen = (int) reader.BaseStream.Length - (int) reader.BaseStream.Position;
                    var cryptCode = reader.ReadBytes(cryptCodeLen);

                    // TODO: Fetch SessionKey from DB
                    decryptedMsg = Encryption.Decrypt(cryptCode, "123456789");

                    Console.WriteLine(decryptedMsg.HexDump());
                }
            }

            using (var ms = new MemoryStream(decryptedMsg))
            {
                using (var reader = new BinaryReader(ms))
                {
                    var count1 = reader.ReadByte();
                    var count2 = reader.ReadByte();
                    var count3 = reader.ReadByte();
                    var count4 = reader.ReadByte();

                    var len = count4 * 256 * 256 * 256 + count3 * 256 * 256 + count2 * 256 + count1;
                    if (len < 0)
                    {
                        Payload = new byte[0];
                        Console.WriteLine("Corrupt.");
                        return;
                    }

                    Payload = reader.ReadBytes(len);
                    Messages = Class1.ParseUpMsg(Payload);
                }
            }
        }
    }
}